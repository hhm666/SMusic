package net.mcpes.hhm.nk.music.listener

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import net.mcpes.hhm.nk.music.SMusic

/**
 * SMusic-New
 *
 * Package: net.mcpes.hhm.nk.music.listener
 * @author hhm Copyright (c) 2019/2/19 15:09
 * version 1.0
 */
class PlayerListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        SMusic.songPlayers[event.player.name] = event.player
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        SMusic.songPlayers.remove(event.player.name)
    }
}