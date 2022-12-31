import DataStoreUtils.readFromRxDatastore
import DataStoreUtils.readQuick
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.rxjava3.rxPreferencesDataStore
import androidx.datastore.rxjava3.RxDataStore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val DATASTORE_USER = "DATASTORE_USER"
object DataStoreUtils {
    val Context.userDataStore: RxDataStore<Preferences> by rxPreferencesDataStore(name = DATASTORE_USER)
    /* rx kotlin */

    @OptIn(ExperimentalCoroutinesApi::class)
    fun RxDataStore<Preferences>.writeToRxDatastore(key: String, value: String): Observable<Preferences> {
        return this.updateDataAsync { prefsIn ->
            val mutablePreferences: MutablePreferences = prefsIn.toMutablePreferences()
            val stringKey  = stringPreferencesKey(key)
            mutablePreferences[stringKey] = value
            Single.just(mutablePreferences)
        }.toObservable()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun RxDataStore<Preferences>.readFromRxDatastore(key: String) : Observable<String>? {
        val stringKey  = stringPreferencesKey(key)
        return this.data().map { pref -> pref.get(stringKey).toString() }.toObservable()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun RxDataStore<Preferences>.clearRxDataStore(): Observable<Preferences> {
        return this.updateDataAsync{ prefsIn ->
            val mutablePreferences: MutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences.clear()
            Single.just(mutablePreferences)
        }.toObservable()
    }

    fun RxDataStore<Preferences>.writeQuick(key: String, value: String,onSuccess: (String) -> Unit){
        this.writeToRxDatastore(key,value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
//                this.readQuick(key){
                    onSuccess("")
//                }
            }
    }

    fun RxDataStore<Preferences>.readQuick(key: String, onSuccess : (String) -> Unit){
        Log.d("rx","user")

        this.readFromRxDatastore(key)
            ?.first("")
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    Log.d("rx",it ?: "empty")
                    onSuccess(it)
                },
                {
                    Log.d("rx",it.message ?: "failed to read from datastore")
                }
            )
    }
}