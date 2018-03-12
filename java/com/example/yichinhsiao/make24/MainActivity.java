package com.example.yichinhsiao.make24;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.mariuszgromada.math.mxparser.Expression;
import make_number.MakeNumber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Button number1, number2, number3, number4, add, subtract, multiply, divide,
            parentheses_left, parentheses_right, delete, done;
    private TextView time, attempt, succeeded, skipped, expression;
    private Random random;
    private Timer timer;
    private TimerTask timer_task;
    private Handler handler;
    private boolean timer_starter;
    private boolean[] number_locker;
    private int puzzle_time;
    private String check;
    private Expression my_expression;
    private MakeNumber make_number;
    private Snackbar snackbar;
    private AlertDialog correct_dialog, show_me_dialog;
    private AlertDialog.Builder correct_dialog_builder, show_me_dialog_builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        correct_dialog_builder = new AlertDialog.Builder(this);
        show_me_dialog_builder = new AlertDialog.Builder(this);

        handler = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if(message.what == 1){
                    int min = puzzle_time / 60;
                    int sec = puzzle_time % 60;
                    String s = "";
                    if(sec < 10){
                        s = min + ":0" + sec;
                    }
                    else{
                        s = min + ":" + sec;
                    }
                    time.setText(s);
                }
            }
        };

        timer_task = new TimerTask() {
            @Override
            public void run() {
                if(timer_starter) {
                    puzzle_time++;
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        };

        time = (TextView) findViewById(R.id.time);
        attempt = (TextView) findViewById(R.id.attempt);
        succeeded = (TextView) findViewById(R.id.succeeded);
        skipped = (TextView) findViewById(R.id.skipped);
        expression = (TextView) findViewById(R.id.expression);
        number1 = (Button) findViewById(R.id.number1);
        number2 = (Button) findViewById(R.id.number2);
        number3 = (Button) findViewById(R.id.number3);
        number4 = (Button) findViewById(R.id.number4);
        add = (Button) findViewById(R.id.add);
        subtract = (Button) findViewById(R.id.subtract);
        multiply = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);
        parentheses_left = (Button) findViewById(R.id.parentheses_left);
        parentheses_right = (Button) findViewById(R.id.parentheses_right);
        delete = (Button) findViewById(R.id.delete);
        done = (Button) findViewById(R.id.done);

        make_number = new MakeNumber();
        setRandomNumber();

        number_locker = new boolean[]{true, true, true, true};

        timer = new Timer();
        timer.schedule(timer_task, 0, 1000);
        timer_starter = true;
        puzzle_time = 0;

        number1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(number1.getText());
                    number1.setEnabled(false);
                    unlockNumber(false);
                    done.setEnabled(true);
                }
            }
        });

        number2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(number2.getText());
                    number2.setEnabled(false);
                    unlockNumber(false);
                    done.setEnabled(true);
                }
            }
        });

        number3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(number3.getText());
                    number3.setEnabled(false);
                    unlockNumber(false);
                    done.setEnabled(true);
                }
            }
        });

        number4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(number4.getText());
                    number4.setEnabled(false);
                    unlockNumber(false);
                    done.setEnabled(true);
                }
            }
        });

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(add.getText());
                    unlockNumber(true);
                    done.setEnabled(true);
                }
            }
        });

        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(subtract.getText());
                    unlockNumber(true);
                    done.setEnabled(true);
                }
            }
        });

        multiply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(multiply.getText());
                    unlockNumber(true);
                    done.setEnabled(true);
                }
            }
        });

        divide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(divide.getText());
                    unlockNumber(true);
                    done.setEnabled(true);
                }
            }
        });

        parentheses_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(parentheses_left.getText());
                    unlockNumber(true);
                    done.setEnabled(true);
                }
            }
        });

        parentheses_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() < 20) {
                    expression.append(parentheses_right.getText());
                    unlockNumber(true);
                    done.setEnabled(true);
                }
            }
        });

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() > 0) {
                    char temp = expression.getText().charAt(expression.getText().length()-1);
                    if(temp == number1.getText().charAt(0) && !number_locker[0]){
                        number1.setEnabled(true);
                        unlockNumber(true);
                    }
                    else if(temp == number2.getText().charAt(0) && !number_locker[1]){
                        number2.setEnabled(true);
                        unlockNumber(true);
                    }
                    else if(temp == number3.getText().charAt(0) && !number_locker[2]){
                        number3.setEnabled(true);
                        unlockNumber(true);
                    }
                    else if(temp == number4.getText().charAt(0) && !number_locker[3]){
                        number4.setEnabled(true);
                        unlockNumber(true);
                    }
                    expression.setText(expression.getText().toString().toCharArray(), 0,
                            expression.getText().length() - 1);
                    if(expression.getText().length() == 0) {
                            done.setEnabled(false);
                    }
                    else if(expression.getText().length() > 0 &&
                            Character.isDigit(expression.getText().charAt(expression.getText().length()-1))) {
                        unlockNumber(false);
                        done.setEnabled(true);
                    }
                    else {
                        done.setEnabled(true);
                    }
                }
            }
        });

        done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expression.getText().length() > 0) {
                    String answer = expression.getText().toString();
                    my_expression = new Expression();
                    my_expression.setExpressionString(answer);
                    Double result = my_expression.calculate();
                    if(Math.abs(result - 24.0) <= 0.0000001 && !number1.isEnabled() &&
                            !number2.isEnabled() && !number3.isEnabled() && !number4.isEnabled()){
                        timer_starter = false;
                        attempt.setText(String.valueOf(Integer.parseInt(attempt.getText().toString()) + 1));
                        correct_dialog_builder.setMessage("Bingo! " + answer + "=24");
                        correct_dialog_builder.setPositiveButton("New Puzzle",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        succeeded.setText(String.valueOf(Integer.parseInt(succeeded.getText().toString()) + 1));
                                        clearExpression();
                                        setRandomNumber();
                                        attempt.setText("1");
                                        time.setText("0:00");
                                        puzzle_time = 0;
                                        timer_starter = true;
                                    }
                                });
                        correct_dialog = correct_dialog_builder.create();
                        correct_dialog.show();
                    }
                    else{
                        snackbar = Snackbar.make(view, "Incorrect, try it again!", Snackbar.LENGTH_LONG);
                        snackbar.setAction("Dismiss", new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                        attempt.setText(String.valueOf(Integer.parseInt(attempt.getText().toString()) + 1));
                        done.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("number1")) {
            number1.setText(String.valueOf(bundle.getInt("number1")));
            number2.setText(String.valueOf(bundle.getInt("number2")));
            number3.setText(String.valueOf(bundle.getInt("number3")));
            number4.setText(String.valueOf(bundle.getInt("number4")));
            skipped.setText(String.valueOf(Integer.parseInt(skipped.getText().toString()) + 1));
            clearExpression();
            attempt.setText("1");
            time.setText("0:00");
            puzzle_time = 0;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                return true;
            case R.id.clear_expression:
                clearExpression();
                return true;
            case R.id.skip_puzzle:
                skipped.setText(String.valueOf(Integer.parseInt(skipped.getText().toString()) + 1));
                clearExpression();
                setRandomNumber();
                attempt.setText("1");
                time.setText("0:00");
                puzzle_time = 0;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.show_me) {
            check = make_number.getSolution(Integer.parseInt(number1.getText().toString()),
                                            Integer.parseInt(number2.getText().toString()),
                                            Integer.parseInt(number3.getText().toString()),
                                            Integer.parseInt(number4.getText().toString()));
            if(check != null){
                CharSequence show_me_answer = check;
                show_me_dialog_builder.setMessage("Answer is " + show_me_answer);
            }
            else{
                show_me_dialog_builder.setMessage("Sorry, there are actually no solutions");
            }
            timer_starter = false;
            show_me_dialog_builder.setPositiveButton("New Puzzle",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            skipped.setText(String.valueOf(Integer.parseInt(skipped.getText().toString()) + 1));
                            clearExpression();
                            setRandomNumber();
                            attempt.setText("1");
                            time.setText("0:00");
                            puzzle_time = 0;
                            timer_starter = true;
                        }
                    });
            show_me_dialog = show_me_dialog_builder.create();
            show_me_dialog.show();
        }
        else if (id == R.id.assign_numbers) {
            Intent intent = new Intent(MainActivity.this, AssignNumbersActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setRandomNumber(){
        random = new Random();

        do {
            int num1 = random.nextInt(9)+1;
            number1.setText(String.valueOf(num1));
            int num2 = random.nextInt(9)+1;
            number2.setText(String.valueOf(num2));
            int num3 = random.nextInt(9)+1;
            number3.setText(String.valueOf(num3));
            int num4 = random.nextInt(9)+1;
            number4.setText(String.valueOf(num4));
            check = make_number.getSolution(num1, num2, num3, num4);
        } while(check == null);
    }

    private void unlockNumber(boolean instruction){
        if(instruction){
            if(number_locker[0]){
                number1.setEnabled(true);
            }
            if(number_locker[1]){
                number2.setEnabled(true);
            }
            if(number_locker[2]){
                number3.setEnabled(true);
            }
            if(number_locker[3]){
                number4.setEnabled(true);
            }
        }
        else{
            number_locker[0] = number1.isEnabled();
            number_locker[1] = number2.isEnabled();
            number_locker[2] = number3.isEnabled();
            number_locker[3] = number4.isEnabled();
            number1.setEnabled(false);
            number2.setEnabled(false);
            number3.setEnabled(false);
            number4.setEnabled(false);
        }
    }

    private void clearExpression(){
        expression.setText("");
        done.setEnabled(false);
        number1.setEnabled(true);
        number2.setEnabled(true);
        number3.setEnabled(true);
        number4.setEnabled(true);
    }
}
