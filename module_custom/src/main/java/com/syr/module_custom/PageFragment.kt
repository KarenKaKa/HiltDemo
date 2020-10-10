package com.syr.module_custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.annotation.LayoutRes
import com.syr.module_common.base.BaseFragment

/**
 * Created by songyaru on 2020/9/27.
 */
class PageFragment : BaseFragment() {
    @LayoutRes
    private var sampleLayoutRes = 0

    @LayoutRes
    private var practiceLayoutRes = 0

    companion object {
        fun newInstance(@LayoutRes sampleLayoutRes: Int, @LayoutRes practiceLayoutRes: Int): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt("sampleLayoutRes", sampleLayoutRes)
            args.putInt("practiceLayoutRes", practiceLayoutRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            sampleLayoutRes = args.getInt("sampleLayoutRes")
            practiceLayoutRes = args.getInt("practiceLayoutRes")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)

        val sampleStub = view.findViewById(R.id.sampleStub) as ViewStub?
        sampleStub?.layoutResource = sampleLayoutRes
        sampleStub?.inflate()

        val practiceStub = view.findViewById(R.id.practiceStub) as ViewStub?
        practiceStub?.layoutResource = practiceLayoutRes
        practiceStub?.inflate()
        return view
    }
}