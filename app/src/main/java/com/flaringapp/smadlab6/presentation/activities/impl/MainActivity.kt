package com.flaringapp.smadlab6.presentation.activities.impl

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.flaringapp.smadlab6.R
import com.flaringapp.smadlab6.presentation.activities.MainContract
import com.flaringapp.smadlab6.presentation.activities.navigation.Screen
import com.flaringapp.smadlab6.presentation.fragments.home.impl.HomeFragment
import com.flaringapp.smadlab6.presentation.mvp.BaseActivity
import org.koin.androidx.scope.currentScope

class MainActivity : BaseActivity<MainContract.PresenterContract>(),
    MainContract.ViewContract {

    override val presenter: MainContract.PresenterContract by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onInitPresenter()

        supportFragmentManager.commit {
            add(R.id.fragmentContainer, HomeFragment.newInstance())
        }

        presenter.onStart()
    }

    override fun onInitPresenter() {
        presenter.view = this
    }

    override fun navigateTo(screen: Screen, data: Any?) {
        presenter.onNavigate(screen, data)
    }

    override fun openScreen(fragment: Fragment) {
        supportFragmentManager.commit {
            addToBackStack(null)
            add(R.id.fragmentContainer, fragment)
        }
    }
}
