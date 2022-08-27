package com.blanktheevil.isaacmod

import basemod.BaseMod
import basemod.interfaces.AddAudioSubscriber
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.blanktheevil.isaacmod.relics.utils.RelicManager
import com.blanktheevil.isaacmod.utils.TextureLoaderKt
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.RelicStrings
import com.megacrit.cardcrawl.localization.UIStrings
import java.lang.IllegalStateException
import java.util.*

@Suppress("unused")
@SpireInitializer
class IsaacMod : EditRelicsSubscriber, EditStringsSubscriber, AddAudioSubscriber, PostInitializeSubscriber {
  companion object {
    lateinit var properties: ModProperties
    const val SLURP = "SLURP"

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

      BaseMod.subscribe(IsaacMod())
      Settings.isTestingNeow = true
    }

    data class ModProperties(
      val name: String,
      val version: String,
      val modid: String,
      val author: String,
      val description: String,
    )
  }



  override fun receiveEditRelics() = RelicManager.addRelics()
  override fun receiveEditStrings() {
    BaseMod.loadCustomStringsFile(RelicStrings::class.java, "isaacmod/localization/eng/relics.json")
    BaseMod.loadCustomStringsFile(UIStrings::class.java, "isaacmod/localization/eng/ui.json")
  }
  override fun receiveAddAudio() {
    BaseMod.addAudio(SLURP, "isaacmod/audio/slurp.mp3")
  }

  override fun receivePostInitialize() {
    BaseMod.registerModBadge(
      Texture(Gdx.files.internal("isaacmod/images/badge.png")),
      properties.name,
      properties.author,
      properties.description,
      null
    )
  }
}