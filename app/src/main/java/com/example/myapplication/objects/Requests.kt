package com.example.myapplication.objects

import android.provider.ContactsContract.SyncState.set
import com.example.myapplication.models.Profile
import com.example.myapplication.models.Test
import com.example.myapplication.objects.SupabaseClient.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

object Requests {

    suspend fun getTest(): List<Test> {
        return supabase.from("test").select().decodeList<Test>()
    }

    suspend fun signIn(e: String, p : String) {
        supabase.auth.signInWith(Email){
            email = e
            password = p
        }
    }

    suspend fun getProfile(): List<Profile> {
        return supabase.from("profiles").select().decodeList<Profile>()
    }

}