package ru.nbsp.pushka.presentation.subscription.subscribe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.subscription.params.ParamsFragment
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

class SubscribeActivity : PresentedActivity<SubscribePresenter>(), SubscribeView {

    companion object {
        const val ARG_SOURCE = "arg_source"
    }

    @Inject
    lateinit var presenter: SubscribePresenter
    lateinit var fragment: ParamsFragment
    lateinit var source: PresentationSource

    val subscribeButton: Button by bindView(R.id.subscribe_button)

    // TODO: remove it
    val TEST_ATTRS: JsonObject by lazy<JsonObject> {
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
        val element = gson.fromJson(json, JsonElement::class.java)
        element.asJsonObject
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        BaseApplication.graph.inject(this)

        // TODO: remove it
        intent.putExtra(ARG_SOURCE, fakeSource())

        initArgs()
        initFragment()
        initPresenter(presenter)
        initViews()
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager

        var cachedFragment: Fragment? = fragmentManager.findFragmentById(R.id.container)

        if (cachedFragment == null) {
            cachedFragment = ParamsFragment()

            fragmentManager.beginTransaction().replace(R.id.container, cachedFragment).commitAllowingStateLoss()
        }

        fragment = cachedFragment as ParamsFragment
    }

    private fun initViews() {
        subscribeButton.setOnClickListener {
            presenter.subscribeButtonClicked()
        }
    }

    private fun initArgs() {
        if (intent.extras.containsKey(ARG_SOURCE)) {
            source = intent.extras.getSerializable(ARG_SOURCE) as PresentationSource
        }
    }

    override fun validateFields(): Boolean {
        return fragment.validate()
    }

    override fun getParamsMap(): Map<String, String?> {
        return fragment.getParamsMap()
    }

    fun fakeSource(): PresentationSource {
        val dropdown1 = PresentationControl("dropdown", "title1", TEST_ATTRS)
        val dropdown2 = PresentationControl("dropdown", "title2", TEST_ATTRS)
        val param1 = PresentationParam("param1", true, dropdown1)
        val param2 = PresentationParam("param2", false, dropdown2)

        val testSource = PresentationSource("weather", arrayListOf(param1, param2), "pogoda",
                "best pogoda", "category1", "qwe", "qwe")
        return testSource
    }

    override fun setParams(params: List<PresentationParam>) {
        fragment.setParams(params)
    }

    override fun initPresenter(presenter: SubscribePresenter) {
        presenter.view = this
        presenter.source = source
        super.initPresenter(presenter)
    }
}
