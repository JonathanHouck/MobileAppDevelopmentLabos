package be.howest.nmct.evaluationstudents;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import be.howest.nmct.evaluationstudents.admin.Student;


public class StudentsActivity extends Activity implements StudentsFragment.OnStudentFragmentListener, DiplomagraadFragment.OnDiplomagraadFragmentListener {

    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new StudentsFragment(), "StudentFragment")
                    .commit();
        }

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_students, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void demandStudentDetail(String sEmailStudent) {
        showStudentDetailFragment(sEmailStudent);
    }

    private void showStudentDetailFragment(String sEmailStudent) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        StudentDetailsFragment fragment = StudentDetailsFragment.newInstance(sEmailStudent);
        fragmentTransaction.replace(R.id.container, fragment, "StudentDetailsFragment");
        fragmentTransaction.addToBackStack("show_new_detail");
        fragmentTransaction.commit();
    }

    @Override
    public void demandStudentsDiplomgraad(Student.DIPLOMAGRAAD diplomagraad) {
        showStudentsDiplomagraadFragment(diplomagraad);
    }

    private void showStudentsDiplomagraadFragment(Student.DIPLOMAGRAAD diplomagraad) {
        FragmentManager fragmentManager = getFragmentManager();

        StudentsFragment sf = (StudentsFragment) getFragmentManager().findFragmentByTag("StudentFragment");
        sf.setDiplomagraadStudents(diplomagraad);
        dl.closeDrawer(Gravity.LEFT);

        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, sf);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
