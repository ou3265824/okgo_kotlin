package com.olq.multiple.bean

/**
 * Created by Administrator on 2017/2/10.
 */
data class BaseBean(
        var createdAt: String? = null,
        var objectId: String? = null,
        var sessionToken: String? = null,
        var code: String? = null,
        var error: String? = null
)