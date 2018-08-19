package com.praveen.userfeed.repo

import com.praveen.userfeed.api.ApiHelper
import com.praveen.userfeed.prefs.PreferenceHelper

interface UserFeedRepository: ApiHelper, PreferenceHelper {
    //Currently empty but when any functionality that needs to access the repository needs to be defined here.
}