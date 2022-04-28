package com.example.moduleh5.h5.contract

interface H5Contract {
    interface View {
       fun hideBar()
       fun finishPage()
       fun showBar()
       fun refreshState(state:Boolean)
       fun reLoadStyle(style:Boolean)
    }
}