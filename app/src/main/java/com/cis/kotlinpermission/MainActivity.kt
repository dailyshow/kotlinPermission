package com.cis.kotlinpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    // 마시멜로 버전 이상일 경우에만 권한 요청하도록 한다. 마시멜로보다 낮은 버전은 권한을 요청하지 않아도 되기 때문임.
    fun checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        for (permission: String in permissionList) {
            val chk = checkCallingOrSelfPermission(permission)

            if (chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissionList, 0)
                break
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0

        tv.text = ""

        for (idx in grantResults.indices) { // indices 를 사용하면 인덱스 번호가 들어가게 된다.
            if (grantResults[idx] == PackageManager.PERMISSION_GRANTED) {
                tv.append("${permissionList[idx]} : 허용함\n")
            } else {
                tv.append("${permissionList[idx]} : 허용하지 않음\n")
            }
        }
    }
}
