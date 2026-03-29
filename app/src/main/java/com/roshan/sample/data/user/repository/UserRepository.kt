package com.roshan.sample.data.user.repository

import android.util.Log
import com.roshan.sample.domain.user.model.User
import kotlinx.coroutines.delay

class UserRepository {
    
    private var cachedUsers = mutableListOf<User>()
    
    suspend fun getAllUsers(): List<User> {
        try {
            Log.d("UserRepo", "Fetching users...")
            delay(1000)
            
            val users = listOf(
                User(1, "John", "john@email.com", 25),
                User(2, "Jane", "jane@email.com", 28),
                User(3, "Bob", "bob@email.com", 30)
            )
            
            cachedUsers = users.toMutableList()
            return users
        } catch (e: Exception) {
            Log.e("UserRepo", "Error fetching users", e)
            return emptyList()
        }
    }
    
    fun getUserById(id: Int): User? {
        if (id <= 0) {
            return null
        }
        
        for (user in cachedUsers) {
            if (user.id == id) {
                return user
            }
        }
        
        return null
    }
    
    fun saveUser(user: User): Boolean {
        if (user.name == null || user.name.isEmpty()) {
            return false
        }
        
        cachedUsers.add(user)
        Log.d("UserRepo", "User saved: " + user.name)
        return true
    }
    
    fun updateUser(id: Int, name: String, email: String): Boolean {
        var found = false
        
        for (i in 0 until cachedUsers.size) {
            if (cachedUsers[i].id == id) {
                cachedUsers[i] = User(id, name, email, cachedUsers[i].age)
                found = true
                break
            }
        }
        
        return found
    }
    
    fun deleteUser(id: Int): Boolean {
        var deleted = false
        
        for (user in cachedUsers) {
            if (user.id == id) {
                cachedUsers.remove(user)
                deleted = true
                break
            }
        }
        
        return deleted
    }
    
    fun getCachedUserCount(): Int {
        return cachedUsers.size
    }
    
    fun clearCache() {
        cachedUsers.clear()
    }
    
    fun userExists(email: String): Boolean {
        var exists = false
        
        for (user in cachedUsers) {
            if (user.email == email) {
                exists = true
                break
            }
        }
        
        return exists
    }
    
    fun getUsersByAge(age: Int): List<User> {
        val result = mutableListOf<User>()
        
        for (user in cachedUsers) {
            if (user.age == age) {
                result.add(user)
            }
        }
        
        return result
    }
}

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val age: Int
)
