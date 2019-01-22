package kz.osmiumt.mymyny

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var state = StateBackground.One
    private var tick = 25
    private val full = 255f
    private val empty = 0f
    private val color = MyColor()

    data class MyColor(var red: Float = 255f, var green: Float = 0f, var blue: Float = 0f)

    enum class StateBackground {
        One, Two, Three, Four, Five, Six
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        start()
    }

    private fun start() {
        background.setBackgroundColor(getColor())

        GlobalScope.launch(Dispatchers.Main) { handlerBackground() }

        seekTick.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
                tick = seekParams!!.progress
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {}

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {}
        }
    }

    private suspend fun handlerBackground() {
        while (true) {
            calcColor()
            setState()
            setUI()
            delay((1000 / tick).toLong())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUI() {
        background.setBackgroundColor(getColor())
        r.text = "RED: ${color.red.toInt()}"
        g.text = "GREEN: ${color.green.toInt()}"
        b.text = "BLUE: ${color.blue.toInt()}"
    }

    private fun calcColor() {
        when (state) {
            StateBackground.One -> {
                color.red -= full / tick
                color.green += full / tick
            }
            StateBackground.Two -> {
                color.green -= full / tick
                color.blue += full / tick
            }
            StateBackground.Three -> {
                color.green += full / tick
            }
            StateBackground.Four -> {
                color.blue -= full / tick
                color.red += full / tick
            }
            StateBackground.Five -> {
                color.green -= full / tick
                color.blue += full / tick
            }
            StateBackground.Six -> {
                color.blue -= full / tick
            }
        }
    }

    private fun setState() {
        when (state) {
            StateBackground.One -> {
                if (color.green >= full &&
                    color.red <= empty
                ) {
                    state = StateBackground.Two

                    setColor(empty, full, empty)
                }
            }
            StateBackground.Two -> {
                if (color.blue >= full &&
                    color.green <= empty
                ) {
                    state = StateBackground.Three

                    setColor(empty, empty, full)
                }
            }
            StateBackground.Three -> {
                if (color.green >= full) {
                    state = StateBackground.Four

                    setColor(empty, full, full)
                }
            }
            StateBackground.Four -> {
                if (color.red >= full &&
                    color.blue <= empty
                ) {
                    state = StateBackground.Five

                    setColor(full, full, empty)
                }
            }
            StateBackground.Five -> {
                if (color.blue >= full &&
                    color.green <= empty
                ) {
                    state = StateBackground.Six

                    setColor(full, empty, full)
                }
            }
            StateBackground.Six -> {
                if (color.blue <= empty) {
                    state = StateBackground.One

                    setColor(full, empty, empty)
                }
            }
        }
    }

    private fun getColor(): Int = Color.rgb(color.red.toInt(), color.green.toInt(), color.blue.toInt())

    private fun setColor(r: Float, g: Float, b: Float) {
        color.red = r
        color.green = g
        color.blue = b
    }
}
