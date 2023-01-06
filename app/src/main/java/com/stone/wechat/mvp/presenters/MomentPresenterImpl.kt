package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.MomentModel
import com.stone.wechat.data.models.MomentModelImpl
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.mvp.views.MomentView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

class MomentPresenterImpl : ViewModel(), MomentPresenter {
    private var mView: MomentView? = null
    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    private val momentModel: MomentModel = MomentModelImpl
    private var allMoment: List<MomentVO> = arrayListOf()

    override fun initView(view: MomentView) {
        mView = view
    }

    override fun onTapCreatePost() {
        mView?.navigateToCreateMoment()
    }

    override fun onTapLike(mMomentVO: MomentVO, absoluteAdapterPosition: Int) {

        allMoment.map {

            if (it.momentId == mMomentVO.momentId) {
                it.isLiked = !mMomentVO.isLiked
            }
        }
//        mView?.updateLikeCount(allMoment,absoluteAdapterPosition)

            momentModel.handleLike(
                mMomentVO.momentId,
                !mMomentVO.isLiked,
                onSuccess = {

                },
                onFailure = {

                },
            )



    }

    override fun onTapComment() {
        TODO("Not yet implemented")
    }

    override fun onTapBookMark(vo: MomentVO, absoluteAdapterPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onTapImage() {
        TODO("Not yet implemented")
    }

    override fun onUIReady(owner: LifecycleOwner) {
//        mFireStoreApi.getMoments(
//            onSuccess = {
//                mView?.initMoment(it)
//            },
//            onFailure = {
//                mView?.showError(it)
//            }
//        )
        momentModel.getAllMoments(
            onTapLikeCallBack = { vo->
                allMoment.map {
                    if (it.momentId == vo.momentId) {
                        it.isLiked = vo.isLiked
                        it.likeCount = vo.likeCount
                    }
                }

                mView?.updateLikeCount(allMoment,allMoment.indexOf(vo))
            },
            onSuccess = {
                this.allMoment = it
                mView?.initMoment(it)
            }, onFailure = {
                mView?.showError(it)
            }
        )

    }

    override fun onTapBackButton() {
        TODO("Not yet implemented")
    }
}