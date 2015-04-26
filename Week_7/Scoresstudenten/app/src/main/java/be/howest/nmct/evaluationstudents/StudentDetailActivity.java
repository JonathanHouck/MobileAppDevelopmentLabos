package be.howest.nmct.evaluationstudents;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import be.howest.nmct.evaluationstudents.admin.Student;
import be.howest.nmct.evaluationstudents.data.StudentAdmin;

//http://stackoverflow.com/questions/17553374/android-app-fragments-vs-android-support-v4-app-using-viewpager
public class StudentDetailActivity extends Activity {

    StudentDetailActivityAdapter mStudentDetailActivityAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        mStudentDetailActivityAdapter = new StudentDetailActivityAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mStudentDetailActivityAdapter);

        int position = getIntent().getExtras().getInt("position");
        mViewPager.setCurrentItem(position);
    }

    public class StudentDetailActivityAdapter extends FragmentStatePagerAdapter {
        public StudentDetailActivityAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            //String sEmailStudent = getIntent().getExtras().getString("emailstudent");

            Student s = StudentAdmin.getStudenten().get(i);
            Fragment fragment = StudentDetailsFragment.newInstance(s.getEmailStudent());

            /*Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(StudentDetailsFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);*/
            return fragment;
        }

        @Override
        public int getCount() {
            return StudentAdmin.getStudenten().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Student s = StudentAdmin.getStudenten().get(position);
            String sVolledigeNaam = s.getVoornaamStudent() + " " + s.getNaamStudent();

            return sVolledigeNaam;
        }
    }
}
