package ru.nbsp.pushka.presentation.subscription.subscribe

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import ru.nbsp.pushka.BaseApplication

import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.subscription.params.ParamsFragment
import ru.nbsp.pushka.presentation.subscription.params.ParamsPresenter
import ru.nbsp.pushka.util.IntentUtils
import java.util.*
import javax.inject.Inject

class SubscribeActivity : PresentedActivity<SubscribePresenter>(), SubscribeView {
    val EXTRA_SOURCE = "extra_source"

    lateinit var fragment: ParamsFragment

    @Inject
    lateinit var presenter: SubscribePresenter

    lateinit var TEST_ATTRS: JsonObject
    lateinit var okButton: Button

    override fun setParams(params: List<PresentationParam>) {
        fragment.setParams(params)
    }

    init {
        val gson = Gson()
        val json = "{\"options\": [\n" +
                "                    {\"value\": \"main\", \"name\": \"Главное\"},\n" +
                "                    {\"value\": \"policy\", \"name\": \"Политика\"},\n" +
                "                    {\"value\": \"business\", \"name\": \"Бизнес\"},\n" +
                "                    {\"value\": \"society\", \"name\": \"Общество\"},\n" +
                "                    {\"value\": \"life_style\", \"name\": \"Стиль жизни\"},\n" +
                "                    {\"value\": \"technology\", \"name\": \"Технологии\"},\n" +
                "                    {\"value\": \"realty\", \"name\": \"Недвижимость\"},\n" +
                "                    {\"value\": \"culture\", \"name\": \"Культура\"},\n" +
                "                    {\"value\": \"science\", \"name\": \"Наука\"},\n" +
                "                    {\"value\": \"sport\", \"name\": \"Спорт\"},\n" +
                "                    {\"value\": \"auto\", \"name\": \"Авто\"}\n" +
                "                ]}"
        val jelem = gson.fromJson(json, JsonElement::class.java)
        TEST_ATTRS = jelem.getAsJsonObject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        BaseApplication.graph.inject(this)
        initPresenter(presenter)

        fragment = ParamsFragment()

        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).commitAllowingStateLoss()

        intent.putExtra(EXTRA_SOURCE, fakeSource())

        val source = intent.extras.getSerializable(EXTRA_SOURCE) as PresentationSource
        presenter.setSource(source)

        okButton = findViewById(R.id.ok_button) as Button
        okButton.setOnClickListener({
            presenter.onButtonClick()
        })
    }

    override fun validateFields(): Boolean {
        return fragment.validate()
    }

    override fun getParamsMap(): Map<String, String?> {
        return fragment.getAsMap()
    }

    fun fakeSource(): PresentationSource {
        val dropdown1 = PresentationControl("dropdown", "title1", TEST_ATTRS)
        val dropdown2 = PresentationControl("dropdown", "title2", TEST_ATTRS)
        val param1 = PresentationParam("param1", true, dropdown1)
        val param2 = PresentationParam("param2", false, dropdown2)

        val testSource = PresentationSource("weather", arrayListOf(param1, param2), "pogoda",
                "best pogoda", "category1")
        return testSource
    }

    override fun initPresenter(presenter: SubscribePresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

}
