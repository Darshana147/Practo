package com.example.practo.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.example.practo.Fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import com.example.practo.InterfaceListeners.PharmacyListener
import com.example.practo.InterfaceListeners.ReadAboutHealthFragmentListener
import com.example.practo.R
import kotlinx.android.synthetic.main.navigation_header.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ReadAboutHealthFragmentListener,PharmacyListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var homeFragment: HomeFragment
    private lateinit var reminderFragment: ReminderFragment
    private lateinit var readAboutHealthFragment: ReadAboutHealthFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_main_toolbar.setTitle("Practo")
        setSupportActionBar(activity_main_toolbar)
        initFragmentManager()
        initFragments()
        setFragmentListeners()
//        setUpHeader()
        readAboutHealthFragment.setReadAboutHealthFragmentListener(this)
        syncNavigationView()

        //default Fragment
        setFragmentTransaction(homeFragment)
        nav_view.setNavigationItemSelectedListener(this)

    }


    fun initFragments() {
        homeFragment = HomeFragment()
        reminderFragment = ReminderFragment()
        readAboutHealthFragment = ReadAboutHealthFragment()
    }


    fun syncNavigationView() {
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, activity_main_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            Toast.makeText(this, "drawer pressed", Toast.LENGTH_LONG).show()
            drawer_layout.closeDrawer(GravityCompat.START)

        } else {
            Toast.makeText(this, "back pressed", Toast.LENGTH_LONG).show()
            super.onBackPressed()
        }

    }

    fun initFragmentManager() {
        fragmentManager = supportFragmentManager
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.homeMenuItem-> setFragmentTransaction(homeFragment)

            R.id.readAboutHealthMenuItem -> setFragmentTransaction(readAboutHealthFragment)

            R.id.reminderMenuItem -> setFragmentTransaction(reminderFragment)

            R.id.orderMedicineMenuItem -> {

                val intent = Intent(this, OrderMedicineActivity::class.java)
                startActivity(intent)

            }

        }
        drawer_layout.closeDrawers()
        return true
    }

    fun setFragmentTransaction(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onReadAboutHealthFragmentListener(adapterPosition: Int) {
        setFragmentTransaction(HealthArticleDescriptionFragment())
    }

    override fun onPharmacyClicked() {
       val intent = Intent(this,OrderMedicineActivity::class.java)
        intent.putExtra("fragment","searchMedicineFragment")
        startActivity(intent)
    }

    fun setFragmentListeners(){
        homeFragment.setPharmacyListener(this)
    }

//    fun setUpHeader(){
//        val headerLayout = layoutInflater.inflate(R.layout.navigation_header,null,false)
//        val userImage = headerLayout.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.user_image)
//        userImage.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.capsule,null))
//    }


}
