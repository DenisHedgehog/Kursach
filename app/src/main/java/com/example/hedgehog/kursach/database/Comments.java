package com.example.hedgehog.kursach.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by hedgehog on 11.05.17.
 */
@Entity
public class Comments {

    @Id(autoincrement = true)
    private Long commentId;

    @NotNull
    private String comment;

    private Long userId;

    @ToOne(joinProperty = "userId")
    private Users user;

    private Long filmId;

    @ToOne(joinProperty = "filmId")
    private Films film;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 2111172442)
    private transient CommentsDao myDao;

    @Generated(hash = 1507953103)
    public Comments(Long commentId, @NotNull String comment, Long userId, Long filmId) {
        this.commentId = commentId;
        this.comment = comment;
        this.userId = userId;
        this.filmId = filmId;
    }

    @Generated(hash = 1094291921)
    public Comments() {
    }

    public Long getId() {
        return this.commentId;
    }

    public void setId(Long id) {
        this.commentId = id;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return this.filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1727165598)
    public Users getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UsersDao targetDao = daoSession.getUsersDao();
            Users userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2128144026)
    public void setUser(Users user) {
        synchronized (this) {
            this.user = user;
            userId = user == null ? null : user.getUserId();
            user__resolvedKey = userId;
        }
    }

    @Generated(hash = 1927421762)
    private transient Long film__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 802906683)
    public Films getFilm() {
        Long __key = this.filmId;
        if (film__resolvedKey == null || !film__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FilmsDao targetDao = daoSession.getFilmsDao();
            Films filmNew = targetDao.load(__key);
            synchronized (this) {
                film = filmNew;
                film__resolvedKey = __key;
            }
        }
        return film;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1008618849)
    public void setFilm(Films film) {
        synchronized (this) {
            this.film = film;
            filmId = film == null ? null : film.getFilmId();
            film__resolvedKey = filmId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1072831068)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommentsDao() : null;
    }

    public Long getCommentId() {
        return this.commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

}
