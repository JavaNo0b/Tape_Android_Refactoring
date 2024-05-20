package com.janob.tape_aos

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(user : User) : Long
    @Update
    fun update(user : User)
    @Delete
    fun delete(user : User)
    @Query("SELECT * FROM UserTable")
    fun getAll() : List<User>
    @Query("SELECT * FROM UserTable WHERE id = :id")
    fun getUser(id: Int) : User

    // 이름으로 특정User 찾기
    @Query("SELECT * FROM UserTable WHERE name = :name")
    fun getUserByName(name : String) : User

    // userKey로 특정User 찾기(1=myUser, 0=otherUser)
    @Query("SELECT * FROM UserTable WHERE userKey = :userKey")
    fun getMyUser(userKey: Int) : User

    // 팔로워, 팔로잉 리스트 업데이트(수정)
    @Query("UPDATE UserTable SET followerList =:followerList WHERE name =:name")
    fun updateUserFollowerList(followerList: List<String>?, name: String?)
    @Query("UPDATE UserTable SET followingList =:followingList WHERE name =:name")
    fun updateUserFollowingList(followingList: List<String>?, name: String?)

    @Query("UPDATE UserTable SET followerList =:followerList WHERE userKey =:userKey")
    fun updateUserFollowerListByUserKey(followerList: List<String>?, userKey: Int?)
    @Query("UPDATE UserTable SET followingList =:followingList WHERE userKey =:userKey")
    fun updateUserFollowingListByUserKey(followingList: List<String>?, userKey: Int?)

    // 이름, 코멘트, 이미지 업데이트(수정)
    @Query("UPDATE UserTable SET name =:name WHERE userKey =:userKey")
    fun updateUserNameByUserKey(name : String?, userKey : Int?)
    @Query("UPDATE UserTable SET comment =:comment WHERE userKey =:userKey")
    fun updateUserCommentByUserKey(comment : String?, userKey : Int?)
    @Query("UPDATE UserTable SET userImg =:userImg WHERE userKey =:userKey")
    fun updateUserImgByUserKey(userImg : Bitmap?, userKey : Int?)

    @Query("UPDATE UserTable SET tapeList =:tapeList WHERE userKey =:userKey")
    fun updateTapeListByUserKey(tapeList : List<Tape>?, userKey : Int?)
    @Query("UPDATE UserTable SET likeList =:likeList WHERE userKey =:userKey")
    fun updateLikeListByUserKey(likeList : List<Song>?, userKey : Int?)
}