package com.blanktheevil.isaacmod

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import java.lang.IllegalStateException
import java.util.*

@Suppress("unused")
@SpireInitializer
class IsaacMod {
  companion object {
    lateinit var properties: ModProperties

    @JvmStatic
    fun initialize() {
      try {
        Properties().also {
          it.load(IsaacMod::class.java.getResourceAsStream("/META-INF/isaacmod.prop"))
          properties = ModProperties(
            it.getProperty("name"),
            it.getProperty("version"),
            it.getProperty("id"),
            it.getProperty("author"),
            it.getProperty("description"),
          )
        }
      } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalStateException("Unable to find properties file!")
      }
    }

    data class ModProperties(
      val name: String,
      val version: String,
      val modid: String,
      val author: String,
      val description: String,
    )
  }
}