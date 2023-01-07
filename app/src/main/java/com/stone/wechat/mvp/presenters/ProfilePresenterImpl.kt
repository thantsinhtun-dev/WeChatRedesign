package com.stone.wechat.mvp.presenters

import DataStoreUtils.userDataStore
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.rxjava3.RxDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.MomentModel
import com.stone.wechat.data.models.MomentModelImpl
import com.stone.wechat.data.models.ProfileModel
import com.stone.wechat.data.models.ProfileModelImpl
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.ProfileView
import com.stone.wechat.networks.*

class ProfilePresenterImpl : ViewModel(), ProfilePresenter {
    private var mView: ProfileView? = null
    private var userVO: UserVO? = null


    private val mProfileModel: ProfileModel = ProfileModelImpl
    private val mMomentModel: MomentModel = MomentModelImpl

    private var allSavesMoments: List<MomentVO> = arrayListOf()
    override fun initView(view: ProfileView) {
        mView = view
    }

    override fun onTapEditUserInfo() {
        userVO?.let {
            mView?.showEditUserInfoDialog(it)
        }
    }

    override fun onTapUploadProfile() {
        mView?.pickProfileImage()
    }

    override fun selectedContent(bitmap: Bitmap) {
        userVO?.let {
            mProfileModel.updateProfileImage(
                bitmap,
                it,
                onSuccess = { success ->
//                            mView?.showError(success)
                },
                onFailure = { error ->
                    mView?.showError(error)
                })
        }
    }

    override fun onTapQrCode() {
        userVO?.userId?.let { mView?.showQrCode(it) }
    }

    override fun onUIReady(context: Context, owner: LifecycleOwner) {

        mProfileModel.getProfileData(
            "it",
            onSuccess = { user ->
                this.userVO = user

                if (owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    Log.i("lifecycle_state", owner.lifecycle.currentState.name)
                    Log.i("profile_data", user.toString())
                    mView?.initProfile(user)
                }
            },
            onFailure = { error ->
                mView?.showError(error)
            }
        )
        mMomentModel.getAllSaveMoments(
            onTapLikeCallBack = { vo ->
                allSavesMoments.map {
                    if (it.momentId == vo.momentId) {
                        it.isLiked = vo.isLiked
                        it.likeCount = vo.likeCount
                    }
                }
                mView?.updateLikeCount(allSavesMoments, allSavesMoments.indexOf(vo))
            },
            onSuccess = { moments ->
                moments.sortedByDescending {
                    it.time
                }
                allSavesMoments = moments
                mView?.initMoment(moments)

            }, onFailure = {

            }
        )

//        getUserDataFromStore()
    }

    override fun onUIReady(owner: LifecycleOwner) {

    }

    override fun onTapBackButton() {

    }

    override fun onTapLike(mMomentVO: MomentVO, absoluteAdapterPosition: Int) {
        mMomentModel.handleLike(
            mMomentVO.momentId,
            !mMomentVO.isLiked,
            onSuccess = {

            },
            onFailure = {

            },
        )
    }

    override fun onTapComment() {
    }

    override fun onTapBookMark(vo: MomentVO, absoluteAdapterPosition: Int) {
        mMomentModel.saveMoment(
            momentId = vo.momentId,
            isSaveMoment = !vo.isSaved,
            onSuccess = {

            }, onFailure = {

            }
        )
    }

    override fun onTapImage() {

    }

    private fun getUserDataFromStore() {


//        dataStore?.readQuick(FIRE_STORE_USER_VO_USERID) {
//            Log.d("rx_read", it)

//        }


    }

}