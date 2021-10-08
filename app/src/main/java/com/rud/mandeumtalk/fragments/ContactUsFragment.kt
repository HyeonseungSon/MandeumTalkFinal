package com.rud.mandeumtalk.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.databinding.FragmentContactusBinding
import com.rud.mandeumtalk.main.contactus.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactUsFragment : Fragment() {

	companion object {

		// binding
		lateinit var binding : FragmentContactusBinding

		// Weather
		private val retrofit = Retrofit.Builder()
			.baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		object ApiObject {
			val retrofitService : WeatherInterface by lazy {
				retrofit.create(WeatherInterface::class.java)
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

		binding = FragmentContactusBinding.inflate(layoutInflater)

		// Weather
		val call = ApiObject.retrofitService.GetWeather(data_type, num_of_rows, page_no, base_date, base_time, nx, ny)
		call.enqueue(object : retrofit2.Callback<WeatherDataModel> {
			override fun onResponse(call: Call<WeatherDataModel>, response: Response<WeatherDataModel>) {
				if (response.isSuccessful == true) {

					val skyValue : Int = response.body()!!.response.body.items.item[5].fcstValue.toInt()
					val ptyValue : Int = response.body()!!.response.body.items.item[6].fcstValue.toInt()

					var skyString : String = ""
					var ptyString : String = ""

					Log.d("api", response.body()!!.response.body.items.item[5].category)
					Log.d("api", skyValue.toString())
					Log.d("api", response.body()!!.response.body.items.item[6].category)
					Log.d("api", ptyValue.toString())

					when (skyValue) {
						1 -> {
							skyString = "맑음"
						}
						3 -> {
							skyString = "구름많음"
						}
						4 -> {
							skyString = "흐림"
						}
					}

					when (ptyValue) {
						0-> {
							ptyString = "없음"
						}
						1-> {
							ptyString = "비"
						}
						2 -> {
							ptyString = "비/눈"
						}
						3 -> {
							ptyString = "눈"
						}
						4 -> {
							ptyString = "소나기"
						}
					}

					binding.weatherText.text = "하늘상태 : $skyValue\n강수형태 : $ptyValue\n하늘상태 : $skyString\n강수형태 : $ptyString"


				}
			}
			override fun onFailure(call: Call<WeatherDataModel>, t: Throwable) {
				Log.d("api fail : ", t.message.toString())
			}
		})

		// Home Icon & Home Text
		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_guideFragment)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_guideFragment)
		}
		// Portfolio Icon & Portfolio Text
		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_portfolioFragment)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_portfolioFragment)
		}
		// Board Icon & Board Text
		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
		}
		binding.accountIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_accountFragment)
		}
		binding.accountText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_accountFragment)
		}

		return binding.root
	}
}