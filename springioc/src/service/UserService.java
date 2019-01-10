package service;

import dao.PlayDao;
import dao.SleepDao;

public class UserService {

    private PlayDao playDao;

    private SleepDao sleepDao;

    public void setPlayDao(PlayDao playDao) {
        this.playDao = playDao;
    }

    public void setSleepDao(SleepDao sleepDao) {
        this.sleepDao = sleepDao;
    }

    public void sleepAndPlay(){
        sleepDao.sleep();

        playDao.play();
    }

}
