package com.example.to_dolist1

import android.os.Bundle
import android.widget.Button //добавлен
import android.widget.EditText //добавлен
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random //добавлен

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val numCarsEditText = findViewById<EditText>(R.id.EditText_2)
        val startRaceButton = findViewById<Button>(R.id.Button_2)
        val resultTextView = findViewById<TextView>(R.id.TextView_2)

        startRaceButton.setOnClickListener {
            val numCars = numCarsEditText.text.toString().toInt()
            val cars = List(numCars) { getRandomCar() }
            val raceResult = races(cars)
            resultTextView.text = raceResult
        }
    }

    open class Car(var brand: String, var model: String, var year: Int, val maxSpeed: Int) {
        open fun getInfo() {
            println("Brand: $brand, Model: $model, Year: $year, Max Speed: $maxSpeed km/h")
        }
    }

    class Sedan(brand: String, model: String, year: Int, maxSpeed: Int, val trunkCapacity: Int) :
        Car(brand, model, year, maxSpeed) {
        override fun getInfo() {
            super.getInfo()
            println("Вместимость богажника: $trunkCapacity л.")
        }
    }

    class SUV(brand: String, model: String, year: Int, maxSpeed: Int, val driveType: String) :
        Car(brand, model, year, maxSpeed) {
        override fun getInfo() {
            super.getInfo()
            println("Тип привода: $driveType")
        }
    }

    class Coupe(brand: String, model: String, year: Int, maxSpeed: Int, val horsePower: Int) :
        Car(brand, model, year, maxSpeed) {
        override fun getInfo() {
            super.getInfo()
            println("Лошадиная сила: $horsePower HP")
        }
    }

    class Сabriolet(
        brand: String,
        model: String,
        year: Int,
        maxSpeed: Int,
        val cabinСapacity: Int
    ) :
        Car(brand, model, year, maxSpeed) {
        override fun getInfo() {
            super.getInfo()
            println("Вместимость салона: $cabinСapacity л.")
        }
    }

    class CarBuilder(private val brand: String, private val model: String) {
        private var year = 0
        private var maxSpeed = 0

        fun setYear(year: Int) = apply { this.year = year }
        fun setMaxSpeed(maxSpeed: Int) = apply { this.maxSpeed = maxSpeed }

        fun buildSedan(trunkCapacity: Int): Sedan {
            return Sedan(brand, model, year, maxSpeed, trunkCapacity)
        }

        fun buildSUV(driveType: String): SUV {
            return SUV(brand, model, year, maxSpeed, driveType)
        }

        fun buildCoupe(horsepower: Int): Coupe {
            return Coupe(brand, model, year, maxSpeed, horsepower)
        }

        fun buildСabriolet(cabinСapacity: Int): Сabriolet {
            return Сabriolet(brand, model, year, maxSpeed, cabinСapacity)
        }
    }

    fun getRandomCar(): Car {
        val brands = listOf("Mercedes", "BMW", "Porsche 911", "Mazda")
        val models = listOf("Benz", "X6", "Turbo S", "MX-5 Miata")
        val driveTypes = listOf("FWD", "RWD", "AWD")
        val brand = brands.random()
        val model = models.random()
        val year = Random.nextInt(2000, 2024)
        val maxSpeed = Random.nextInt(150, 300)

        val carBuilder = CarBuilder(brand, model).setYear(year).setMaxSpeed(maxSpeed)

        return when (Random.nextInt(4)) {
            0 -> carBuilder.buildSedan(Random.nextInt(300, 700))
            1 -> carBuilder.buildSUV(driveTypes.random())
            2 -> carBuilder.buildCoupe(Random.nextInt(200, 800))
            3 -> carBuilder.buildСabriolet(Random.nextInt(10, 50))
            else -> carBuilder.buildSedan(Random.nextInt(300, 700))
        }
    }

    fun races(cars: List<Car>): String {
        val Result = StringBuilder()
        var сircle = 1
        var rivals = cars.shuffled() //перемешиваем машинки

        while (rivals.size > 1) {
            Result.append("<--- Круг $сircle --->\n")
            val winners = mutableListOf<Car>()

            for (i in rivals.indices step 2) {
                if (i + 1 < rivals.size) {
                    val car1 = rivals[i]
                    val car2 = rivals[i + 1]
                    val winner = if (car1.maxSpeed > car2.maxSpeed) car1 else car2
                    winners.add(winner)
                    Result.append("Гонка между ${car1.model} и ${car2.model}, Победитель: ${winner.model}\n")
                } else {
                    winners.add(rivals[i])
                    Result.append("${rivals[i].model} - Нет пары, следующий круг\n")
                }
            }

            rivals = winners
            сircle++
        }

        Result.append("Победитель последнего заезда ${rivals.first().model}\n")
        return Result.toString()
    }
}


