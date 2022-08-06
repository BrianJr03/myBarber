package jr.brian.mybarber

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import jr.brian.mybarber.model.data.Constant
import jr.brian.mybarber.model.data.Repository
import jr.brian.mybarber.model.data.barber.BarberResponse
import jr.brian.mybarber.viewmodel.barbers.BarberViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CategoryViewModelTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var barberObserver: Observer<BarberResponse>

    @Mock
    lateinit var processingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<String>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: BarberViewModel

    @Before
    fun setUp() {
        viewModel = BarberViewModel(repository)

        viewModel.barberResponse.observeForever(barberObserver)
//        viewModel.isProcessing.observeForever(processingObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun `getCategory failure testing`() {
        runBlockingTest {
            val dummyResponse = Response.error<String>(
                500,
                ResponseBody.create(
                    "text/plain".toMediaType(),
                    "Internal server error"
                )
            )
            doReturn(dummyResponse).`when`(repository).getBarbers()
            viewModel.getBarbers()

//            verify(processingObserver).onChanged(true)
            verify(repository).getBarbers()
//            verify(processingObserver).onChanged(false)

            val expectedResult = "Failed to load data. Error code: 500"

            verify(errorObserver).onChanged(expectedResult)
            viewModel.error.removeObserver(errorObserver)

        }
    }

    @Test
    fun `getCategory success testing`() {
        runBlockingTest {
            val dummyResponse = Response.success(
                Gson().fromJson(Constant.BARBER_SUCCESS_RESPONSE, BarberResponse::class.java)
            )

            doReturn(dummyResponse).`when`(repository).getBarbers()

            viewModel.barberResponse.observeForever(barberObserver)
//            viewModel.processing.observeForever(processingObserver)
            viewModel.getBarbers()

            verify(processingObserver).onChanged(true)
            verify(repository).getBarbers()
            verify(processingObserver).onChanged(false)

            val expectedResult =
                Gson().fromJson(Constant.BARBER_SUCCESS_RESPONSE, BarberResponse::class.java)

            verify(barberObserver).onChanged(expectedResult)

            viewModel.barberResponse.removeObserver(barberObserver)
//            viewModel.processing.removeObserver(processingObserver)

        }
    }

    @Test(expected = RuntimeException::class)
    fun `loadCategories exception testing`() = runBlocking {
        val exception = RuntimeException("Internet is not available")

        doThrow(exception).`when`(repository.getBarbers())

        viewModel.error.observeForever(errorObserver)
//        viewModel.processing.observeForever(processingObserver)
        viewModel.getBarbers()

//        verify(processingObserver).onChanged(true)
        verify(repository).getBarbers()
//        verify(processingObserver).onChanged(false)

        val expectedResult = "Error is : $exception"

        verify(errorObserver).onChanged(expectedResult)

        viewModel.error.removeObserver(errorObserver)
//        viewModel.processing.removeObserver(processingObserver)
    }


    @After
    fun tearDown() {
        viewModel.barberResponse.removeObserver(barberObserver)
//        viewModel.processing.removeObserver(processingObserver)
        viewModel.error.removeObserver(errorObserver)
    }
}