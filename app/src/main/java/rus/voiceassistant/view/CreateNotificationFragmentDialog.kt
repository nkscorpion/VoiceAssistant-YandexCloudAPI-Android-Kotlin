package rus.voiceassistant.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.elmargomez.typer.Font
import com.elmargomez.typer.Typer
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.create_notification.*
import rus.voiceassistant.Logger
import rus.voiceassistant.R
import rus.voiceassistant.model.Notification
import java.util.*

/**
 * Created by RUS on 30.04.2016.
 */
class CreateNotificationFragmentDialog() : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    companion object {
        fun newInstance(): CreateNotificationFragmentDialog {
            return CreateNotificationFragmentDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.create_notification, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog.setTitle(R.string.addNotfication)

        btnCancel.typeface = Typer.set(context).getFont(Font.ROBOTO_MEDIUM)
        btnOk.typeface = Typer.set(context).getFont(Font.ROBOTO_MEDIUM)

        editNotificationDate.setOnFocusChangeListener { view, b -> if(b) showDatePicker()  }
        editNotificationTime.setOnFocusChangeListener { view, b -> if(b) showTimePicker()  }

        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener { finish() }
    }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val dpd: DatePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show(activity.fragmentManager, "DatePicker")
    }

    fun showTimePicker() {
        val tpd: TimePickerDialog = TimePickerDialog.newInstance(this, 0, 0, true);
        tpd.show(activity.fragmentManager, "TimePicker")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        notificationDateText.visibility = View.VISIBLE
        editNotificationDate.setText("$dayOfMonth.$monthOfYear.$year")

    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int, second: Int) {
        notificationTimeText.visibility = View.VISIBLE
        editNotificationTime.setText("$hourOfDay:$minute")
    }

    fun finish() {
        val notification = Notification()
        notification.time = "${editNotificationTime.text}"
        notification.text = "${editNotificationText.text}"
        val notificationsFragment = targetFragment as NotificationCreationListener
        notificationsFragment.onNotificationCreated(notification)
        dismiss()
    }

}