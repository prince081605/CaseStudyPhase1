package com.example.casestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.casestudy.data.Announcement
import com.example.casestudy.data.AppDatabase
import com.example.casestudy.ui.navigation.AppNavHost
import com.example.casestudy.ui.theme.CaseStudyTheme
import com.example.casestudy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(db.dao()) as T
            }
        })[MainViewModel::class.java]

        // Pre-populate announcements if empty
        lifecycleScope.launch {
            val currentAnnouncements = db.dao().getAllAnnouncements().first()
            if (currentAnnouncements.isEmpty()) {
                db.dao().insertAnnouncement(
                    Announcement(
                        title = "Welcome to Smart CC!",
                        content = "This is your new campus companion app. Stay tuned for more updates.",
                        date = "Oct 24, 2023"
                    )
                )
                db.dao().insertAnnouncement(
                    Announcement(
                        title = "Midterm Examination Schedule",
                        content = "Midterms start next Monday. Please check your portals for room assignments.",
                        date = "Oct 25, 2023"
                    )
                )
            }
        }

        setContent {
            CaseStudyTheme {
                AppNavHost(viewModel = viewModel)
            }
        }
    }
}
