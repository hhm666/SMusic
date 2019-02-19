package net.mcpes.hhm.nk.music.task

import cn.nukkit.Server
import cn.nukkit.scheduler.AsyncTask
import net.mcpes.hhm.nk.music.SMusic

/**
 * SMusic-New
 *
 * Package: net.mcpes.hhm.nk.music.task
 * @author hhm Copyright (c) 2019/2/19 15:46
 * version 1.0
 */
class MusicPlayingTask : AsyncTask() {
    override fun onRun() {
        while (Server.getInstance().isRunning) {
            Thread.sleep(20)
            if (SMusic.playing && SMusic.songPlayers.isNotEmpty()) {
                SMusic.broadcast()
            }
        }
    }
}