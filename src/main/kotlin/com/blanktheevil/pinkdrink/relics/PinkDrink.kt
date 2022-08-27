package com.blanktheevil.pinkdrink.relics

import com.blanktheevil.pinkdrink.PinkDrinkMod
import com.blanktheevil.pinkdrink.makeID
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip

class PinkDrink : Relic(ID, IMG, TIER, SOUND), ClickableRelic {
  companion object {
    val ID = "PinkDrink".makeID()
    val IMG = "${PinkDrinkMod.properties.modid}/images/pinkdrink"
    val TIER = RelicTier.SPECIAL
    val SOUND = LandingSound.MAGICAL
  }

  private var isReady = false

  override fun setCounter(counter: Int) {
    if (counter == -2) {
      this.counter = -2
      grayscale = true
    }
  }

  override fun getUpdatedDescription(): String {
    return DESCRIPTIONS[0] + if (isReady) " NL NL ${DESCRIPTIONS[1]}" else ""
  }

  override fun wasHPLost(damageAmount: Int) {
    AbstractDungeon.player.let {
      if (it.currentHealth.minus(damageAmount).div(it.maxHealth.toFloat()) <= 0.3 && counter != -2) {
        isReady = true
        setDescription(updatedDescription)
        beginLongPulse()
      }
    }
  }

  override fun onVictory() {
    isReady = false
    stopPulse()
    setDescription(updatedDescription)
  }

  override fun onRightClick() {
    if (isReady && counter != -2) {
      CardCrawlGame.sound.play(PinkDrinkMod.SLURP)
      counter = -2
      grayscale = true
      stopPulse()
      AbstractDungeon.actionManager.addToBottom(HealAction(
        AbstractDungeon.player,
        AbstractDungeon.player,
        AbstractDungeon.player.maxHealth)
      )
    } else {
      CardCrawlGame.sound.play(PinkDrinkMod.SLURP, 0.5f)
    }
  }

  private fun setDescription(description: String) {
    this.tips.clear()
    this.tips.add(
      PowerTip(name, description)
    )

    initializeRelicTips()
  }
}