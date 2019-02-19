package net.mcpes.hhm.nk.music.command

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import net.mcpes.hhm.nk.music.SMusic
import net.mcpes.hhm.nk.music.SMusic.Companion.RANDOM_PLAY
import net.mcpes.summit.hhm.base.utils.language.translate
import java.io.File

/**
 * SMusic-New
 *
 * Package: net.mcpes.hhm.nk.music.command
 * @author hhm Copyright (c) 2019/2/19 15:47
 * version 1.0
 */
class MusicCommand : Command("music", "SMusic Master Command") {
    override fun execute(sender: CommandSender, s: String, strings: Array<out String>): Boolean {
        if (strings.isEmpty()) {
            sender.sendMessage("smusic.command.notFound" translate arrayOf())
            return false
        }
        when (strings[0]) {
            "开始", "start" -> {
                if (sender !is Player) {
                    sender.sendMessage("smusic.command.notPlayer" translate arrayOf())
                    return true
                }
                SMusic.songPlayers[sender.name] = sender
                sender.sendMessage("smusic.command.start.success" translate arrayOf())
            }
            "暂停", "pause" -> {
                if (sender !is Player) {
                    sender.sendMessage("smusic.command.notPlayer" translate arrayOf())
                    return true
                }
                SMusic.songPlayers.remove(sender.name)
                sender.sendMessage("smusic.command.pause.success" translate arrayOf())
            }
            "加载", "load" -> {
                if (!sender.isOp) {
                    sender.sendMessage("smusic.command.notOp" translate arrayOf())
                    return false
                }
                if (strings.size == 2) {
                    val file = File(SMusic.instance.dataFolder.absolutePath + "/songs/" + strings[1] + ".nbs")
                    if (file.exists()) {
                        SMusic.loadSong(file)
                        sender.sendMessage("smusic.command.load.success" translate arrayOf(strings[1]))
                    } else {
                        sender.sendMessage("smusic.command.load.notExists" translate arrayOf(strings[1]))
                    }
                } else {
                    sender.sendMessage("smusic.command.notFound" translate arrayOf())
                }
            }
            "下一首", "next" -> {
                if (!sender.isOp) {
                    sender.sendMessage("smusic.command.notOp" translate arrayOf())
                    return true
                }
                if (strings.size != 1) {
                    sender.sendMessage("smusic.command.notFound" translate arrayOf())
                    return false
                }
                SMusic.next()
            }
            "上一首", "previous" -> {
                if (!sender.isOp) {
                    sender.sendMessage("smusic.command.notOp" translate arrayOf())
                    return true
                }
                if (strings.size != 1) {
                    sender.sendMessage("smusic.command.notFound" translate arrayOf())
                    return false
                }
                if (SMusic.playMode == RANDOM_PLAY) {
                    sender.sendMessage("smusic.command.previous.random" translate arrayOf())
                } else {
                    SMusic.previous()
                }
            }
            "播放", "play" -> {
                if (!sender.isOp) {
                    sender.sendMessage("smusic.command.notOp" translate arrayOf())
                    return false
                }
                if (strings.size == 2) {
                    when (strings[1]) {
                        "开启", "on" -> {
                            SMusic.playing = true
                            sender.sendMessage("smusic.command.play.on" translate arrayOf())
                        }
                        "关闭", "off" -> {
                            SMusic.playing = false
                            sender.sendMessage("smusic.command.play.off" translate arrayOf())
                        }
                        else -> {
                            sender.sendMessage("smusic.command.notFound" translate arrayOf())
                            return false
                        }
                    }
                }
            }
            "帮助", "help" -> {
                if (!sender.isOp) {
                    sender.sendMessage("smusic.command.help.player" translate arrayOf())
                } else {
                    sender.sendMessage("smusic.command.help.admin" translate arrayOf())
                }
            }
            "info" -> {
                sender.sendMessage("smusic.command.copyright" translate arrayOf(SMusic.instance.fullName))
            }
            else -> {
                sender.sendMessage("smusic.command.notFound" translate arrayOf())
                return false
            }
        }
        return true
    }
}