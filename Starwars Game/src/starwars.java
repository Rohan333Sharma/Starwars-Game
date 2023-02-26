import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.Random;

class EnemyPanel extends Panel
{
    Label l1,l2;
    JLabel enemyl;
    ImageIcon enemy_img;
    int l = 1;
    EnemyPanel()
    {
        l1 = new Label();
        l2 = new Label();
        enemyl = new JLabel();
        enemy_img = new ImageIcon("enemy.jpg");
        l1.setBounds(10, 0, 20, 5);
        l2.setBounds(30, 0, 20, 5);
        enemyl.setBounds(0, 10, 64, 39);
        l1.setBackground(Color.RED);
        l2.setBackground(Color.RED);
        enemyl.setIcon(enemy_img);
        setLayout(null);
        add(l1);
        add(l2);
        add(enemyl);
    }
}

class starwars extends WindowAdapter implements KeyListener
{
   Frame f;
   Panel upper, mainp, health;
   Label bullet, enemybulletm, enemybulletl, enemybulletr, l1, l2, l3, l4;
   JLabel score, timer;
   JLabel function;
   EnemyPanel enemy, enemy_left, enemy_right;
   JButton ship, pause;
   Font ft;
   ImageIcon ship_img, boom_img, pause_img, resume_img, shipboom_img, shipboom2_img, shipboom3_img;
   Thread shootThread, timerThread, mainEnemyThread, leftEnemyThread, rightEnemyThread;
   Thread enemybulletmThread, enemybulletlThread, enemybulletrThread, shipboomThread;
   boolean b = true, boom = true, booml = true, boomr = true, shipboom = true;
   int j,jr,jl,sc=0,time=1,h=3;
   String msg="Time's Up !!";
   String controls= "Left:  <  Fire:  F  Right:  >";
   
   starwars()
   {
        f = new Frame();
        upper = new Panel();
        mainp = new Panel();
        score = new JLabel();
        timer = new JLabel();
        function = new JLabel(controls);// fire f left <- right ->
        pause = new JButton();
        bullet = new Label();
        enemybulletm = new Label();
        enemybulletl = new Label();
        enemybulletr = new Label();
        ship = new JButton();
        enemy = new EnemyPanel();
        enemy_left = new EnemyPanel();
        enemy_right = new EnemyPanel();
        health = new Panel();
        l1 = new Label();
        l2 = new Label();
        l3 = new Label();
        l4 = new Label();
        try{
        ft = Font.createFont(Font.TRUETYPE_FONT, new File("BigSpace.ttf")).deriveFont(22f);
        }
        catch(Exception e){}
        ship_img = new ImageIcon("ship1.jpg");
        shipboom_img = new ImageIcon("shipboom.jpg");
        boom_img = new ImageIcon("boom.jpg");
        pause_img = new ImageIcon("pause.jpg");
        resume_img = new ImageIcon("resume.jpg");

        ship.addActionListener((e)->
        {
            if(b)
            {
                int bx = bullet.getX();
                ship.setFocusable(false);
                shootThread = new Thread(()->
                {   
                    for(int i=450;i>0;i-=10)
                    {
                        try
                        {
                            bullet.setBounds(bx,i,2,8);
                            if(bullet.getY()>=enemy.getY()&&bullet.getY()<=enemy.getY()+46&&bullet.getX()>=enemy.getX()&&bullet.getX()<=enemy.getX()+64)
                            {
                                if(enemy.l == 1)
                                {
                                    enemy.l2.setVisible(false);
                                    enemy.l = 0;
                                    break;
                                }
                                else if(enemy.l == 0)
                                {
                                    enemy.l1.setVisible(false);
                                    
//                                    sc = sc + 10;
//                                    score.setText("Score: " + sc);
                                    boom = false;
                                    break;
                                }
                            }
                            else if(bullet.getY()>=enemy_left.getY()&&bullet.getY()<=enemy_left.getY()+46&&bullet.getX()>=enemy_left.getX()&&bullet.getX()<=enemy_left.getX()+64)
                            {
                                if(enemy_left.l == 1)
                                {
                                    enemy_left.l2.setVisible(false);
                                    enemy_left.l = 0;
                                    break;
                                }
                                else if(enemy_left.l == 0)
                                {
                                    enemy_left.l1.setVisible(false);
//                                    sc = sc + 10;
//                                    score.setText("Score: " + sc);
                                    booml = false;
                                    break;
                                }
                            }
                            else if(bullet.getY()>=enemy_right.getY()&&bullet.getY()<=enemy_right.getY()+46&&bullet.getX()>=enemy_right.getX()&&bullet.getX()<=enemy_right.getX()+64)
                            {
                                if(enemy_right.l == 1)
                                {
                                    enemy_right.l2.setVisible(false);
                                    enemy_right.l = 0;
                                    break;
                                }
                                else if(enemy_right.l == 0)
                                {
                                    enemy_right.l1.setVisible(false);
//                                    sc = sc + 10;
//                                    score.setText("Score: " + sc);
                                    boomr = false;
                                    break;
                                }
                            }
                            Thread.sleep(20);
                        }
                        catch(Exception e1)
                        {
                        }   
                    }
                    bullet.setBounds(ship.getX()+31, ship.getY(), 2, 8);
                });
                shootThread.start();
            }
        });

        timerThread = new Thread(()->
        {
            for(int i=60;i>=0;i--)
            {
                if(shipboom==false)
                    break;
                try
                {
                    timer.setText("Time Left: "+i);
                    Thread.sleep(1000);
                }
                catch(Exception e1)
                {
                }
            }
            b=false;
            UIManager.put("OptionPane.minimumSize",new Dimension(200,100));
            UIManager.put("OptionPane.messageForeground", Color.RED);
            UIManager.put("OptionPane.messageFont", ft);
            UIManager.put("OptionPane.background", Color.WHITE);
            UIManager.put("OptionPane.foreground", ft);
            UIManager.put("Panel.background", Color.WHITE);
            int ok = JOptionPane.showOptionDialog(f, score.getText()+"\nKilled: "+sc/10, msg, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,null,null);
            if(ok==JOptionPane.OK_OPTION)
                f.dispose();
        });
        timerThread.start();

        mainEnemyThread = new Thread(()->
        {
            while(b)
            {
                enemy.l2.setVisible(true);
                enemy.l1.setVisible(true);
                enemy.setVisible(true);
                enemy.l = 1;
                Random r = new Random();
                int x1 = r.nextInt(160);
                for(j=0;j<=300;j++)
                {
                    if (boom == false)
                    {
                        enemy.enemyl.setIcon(boom_img);
                        if(enemy.enemyl.getIcon()==boom_img)
                        {
                        	sc = sc + 10;
                        	score.setText("Score: " + sc);
                        }
                        try
                        {
                            Thread.sleep(500);
                            enemy.setVisible(false);
                            j = 600;
                        }
                        catch(Exception eboom)
                        {
                        }
                        boom = true;
                        enemy.enemyl.setIcon(enemy.enemy_img);
                    }
                    if (b==false)
                        break;
                    try
                    {
                        enemy.setBounds(x1+270,j,64,46);
                        Thread.sleep(100);
                    }
                    catch(Exception e1)
                    {
                    }
                }
            }
        });
        mainEnemyThread.start();

        leftEnemyThread = new Thread(()->
        {
            while(b)
            {
                enemy_left.l2.setVisible(true);
                enemy_left.l1.setVisible(true);
                enemy_left.setVisible(true);
                enemy_left.l = 1;
                Random r1 = new Random();
                int x2 = r1.nextInt(160);
                for(jl=0;jl<=300;jl++)
                {
                    if (booml == false)
                    {
                        enemy_left.enemyl.setIcon(boom_img);
                        if(enemy_left.enemyl.getIcon()==boom_img)
                        {
                        	sc = sc + 10;
                        	score.setText("Score: " + sc);
                        }
                        try
                        {
                            Thread.sleep(500);
                            enemy_left.setVisible(false);
                            jl = 600;
                        }
                        catch(Exception ebooml)
                        {
                        }
                        booml = true;
                        enemy_left.enemyl.setIcon(enemy_left.enemy_img);
                    }
                    if (b==false)
                        break;
                    try
                    {
                        enemy_left.setBounds(x2+50,jl,64,46);
                        Thread.sleep(100);
                    }
                    catch(Exception e1)
                    {
                    }
                }
            }
        });
        leftEnemyThread.start();

        rightEnemyThread = new Thread(()->
        {
            while(b)
            {
                enemy_right.l2.setVisible(true);
                enemy_right.l1.setVisible(true);
                enemy_right.setVisible(true);
                enemy_right.l = 1;
                Random r2 = new Random();
                int x3 = r2.nextInt(160);
                for(jr=0;jr<=300;jr++)
                {
                    if (boomr == false)
                    {
                        enemy_right.enemyl.setIcon(boom_img);
                        if(enemy_right.enemyl.getIcon()==boom_img)
                        {
                        	sc = sc + 10;
                        	score.setText("Score: " + sc);
                        }
                        try
                        {
                            Thread.sleep(500);
                            enemy_right.setVisible(false);
                            jr = 600;
                        }
                        catch(Exception eboom)
                        {
                        }
                        boomr = true;
                        enemy_right.enemyl.setIcon(enemy_right.enemy_img);
                    }
                    if (b==false)
                        break; 
                    try
                    {
                        enemy_right.setBounds(x3+490,jr,64,46);
                        Thread.sleep(100);
                    }
                    catch(Exception e1)
                    {
                    }
                }
            }
        });
        rightEnemyThread.start();

        enemybulletmThread = new Thread(()->
        {
            try
            {
                Thread.sleep(1500);
                while(b)
                {
                    enemybulletm.setVisible(true);
                    int ebmx = enemy.getX()+31;
                    for(int i=enemy.getY()+46; i<550;i+=10)
                    {
                        if (b==false)
                            break;
                        enemybulletm.setBounds(ebmx, i, 2, 8);
                        if(ebmx>=ship.getX()&&ebmx<=ship.getX()+64&&enemybulletm.getY()>=ship.getY()&&enemybulletm.getY()<=ship.getY()+88)
                        {
                            if(h==3)
                            {
                                l4.setVisible(false);
                                h--;
                                break;
                            }
                            else if(h==2)
                            {
                                l3.setVisible(false);
                                h--;
                                break;
                            }
                            else if(h==1)
                            {
                                l2.setVisible(false);
                                h--;
                                break;
                            }
                            else
                            {
                                l1.setVisible(false);
                                shipboomThread.start();
                                break;
                            }
                        }
                        Thread.sleep(50);
                    }
                    enemybulletm.setVisible(false);
                    Thread.sleep(2000);
                }
            }
            catch (Exception eebm)
            {
            }
        });
        enemybulletmThread.start();

        enemybulletlThread = new Thread(()->
        {
            try
            {
                Thread.sleep(1500);
                while(b)
                {
                    enemybulletl.setVisible(true);
                    int eblx = enemy_left.getX()+31;
                    for(int i=enemy_left.getY()+46; i<550;i+=10)
                    {
                        if (b==false)
                            break;
                        enemybulletl.setBounds(eblx, i, 2, 8);
                        if(eblx>=ship.getX()&&eblx<=ship.getX()+64&&enemybulletl.getY()>=ship.getY()&&enemybulletl.getY()<=ship.getY()+88)
                        {
                            if(h==3)
                            {
                                l4.setVisible(false);
                                h--;
                                break;
                            }
                            else if(h==2)
                            {
                                l3.setVisible(false);
                                h--;
                                break;
                            }
                            else if(h==1)
                            {
                                l2.setVisible(false);
                                h--;
                                break;
                            }
                            else
                            {
                                l1.setVisible(false);
                                shipboomThread.start();
                                break;
                            }
                        }
                        Thread.sleep(50);
                    }
                    enemybulletl.setVisible(false);
                    Thread.sleep(2000);
                }
            }
            catch (Exception eebl)
            {
            }
        });
        enemybulletlThread.start();

        enemybulletrThread = new Thread(()->
        {
            try
            {
                Thread.sleep(1500);
                while(b)
                {
                    enemybulletr.setVisible(true);
                    int ebrx = enemy_right.getX()+31;
                    for(int i=enemy_right.getY()+46; i<550;i+=10)
                    {
                        if (b==false)
                            break;
                        if(ebrx>=ship.getX()&&ebrx<=ship.getX()+64&&enemybulletr.getY()>=ship.getY()&&enemybulletr.getY()<=ship.getY()+88)
                        {
                            if(h==3)
                            {
                                l4.setVisible(false);
                                h--;
                                break;
                            }
                            else if(h==2)
                            {
                                l3.setVisible(false);
                                h--;
                                break;
                            }
                            else if(h==1)
                            {
                                l2.setVisible(false);
                                h--;
                                break;
                            }
                            else
                            {
                                l1.setVisible(false);
                                shipboomThread.start();
                                break;
                            }
                        }
                        enemybulletr.setBounds(ebrx, i, 2, 8);
                        Thread.sleep(50);
                    }
                    enemybulletr.setVisible(false);
                    Thread.sleep(2000);
                }
            }
            catch (Exception eebr)
            {
            }
        });
        enemybulletrThread.start();

        shipboomThread = new Thread(()->
        {
            try
            {
                Image im = shipboom_img.getImage();
                Image ima = im.getScaledInstance(44, 61, Image.SCALE_SMOOTH);
                Image imag = im.getScaledInstance(24, 33, Image.SCALE_SMOOTH);
                shipboom2_img = new ImageIcon(ima);
                shipboom3_img = new ImageIcon(imag);
                ship.setIcon(shipboom3_img);
                Thread.sleep(300);
                ship.setIcon(shipboom2_img);
                Thread.sleep(300);
                ship.setIcon(shipboom_img);
                msg = "Wasted !!";
                shipboom = false;             
            }
            catch(Exception esboom)
            {
            }
        });

        pause.addActionListener((e)->
        {
            if(pause.getIcon() == pause_img)
            {
                pause.setIcon(resume_img);
                try
                {
                    timerThread.suspend();
                    mainEnemyThread.suspend();
                    leftEnemyThread.suspend();
                    rightEnemyThread.suspend();
                    enemybulletmThread.suspend();
                    enemybulletlThread.suspend();
                    enemybulletrThread.suspend();
                    shootThread.suspend();
                } 
                catch (Exception epause)
                {
                }
            }
            else
            {
                pause.setIcon(pause_img);
                timerThread.resume();
                mainEnemyThread.resume();
                leftEnemyThread.resume();
                rightEnemyThread.resume();
                enemybulletmThread.resume();
                enemybulletlThread.resume();
                enemybulletrThread.resume();
                shootThread.resume();
            }
            pause.setFocusable(false);
        });

        f.addKeyListener(this);
        f.addWindowListener(this);
        f.setFocusable(true);
        ship.setBounds(320, 410, 64, 88);
        ship.setBackground(Color.BLACK);
        ship.setIcon(ship_img);
        ship.setBorder(null);
        health.setLayout(null);
        health.setBounds(332, 500, 64, 10);
        health.add(l1);
        health.add(l2);
        health.add(l3);
        health.add(l4);
        l1.setBounds(0,0,10,5);
        l2.setBounds(10,0,10,5);
        l3.setBounds(20,0,10,5);
        l4.setBounds(30,0,10,5);
        l1.setBackground(Color.GREEN);
        l2.setBackground(Color.GREEN);
        l3.setBackground(Color.GREEN);
        l4.setBackground(Color.GREEN);
        bullet.setBounds(350, 410, 2, 8);
        bullet.setBackground(Color.YELLOW);
        enemybulletm.setBackground(Color.YELLOW);
        enemybulletl.setBackground(Color.YELLOW);
        enemybulletr.setBackground(Color.YELLOW);
        score.setBounds(20,5,120,40);
        score.setFont(ft);
        score.setForeground(Color.GREEN);
        score.setText("Score: " + sc);
        timer.setBounds(170,5,160,40);
        timer.setFont(ft);
        timer.setForeground(Color.WHITE);
        function.setBounds(370,5,215,40);
        function.setForeground(Color.WHITE);
        function.setFont(ft);
        pause.setBounds(640,5,40,40);
        pause.setIcon(pause_img);
        upper.setLayout(null);
        upper.add(score);
        upper.add(timer);
        upper.add(function);
        upper.add(pause);
        upper.setBounds(0,30,700,50);
        upper.setBackground(Color.BLACK);
        mainp.setBounds(0,80,700,550);
        mainp.setLayout(null);
        mainp.setBackground(Color.BLACK);
        mainp.add(ship);
        mainp.add(health);
        mainp.add(bullet);
        mainp.add(enemybulletm);
        mainp.add(enemybulletl);
        mainp.add(enemybulletr);
        mainp.add(enemy);
        mainp.add(enemy_left);
        mainp.add(enemy_right);
        f.setLayout(null);
        f.add(upper);
        f.add(mainp);
        f.setSize(700,600);
        f.setVisible(true);
   }

   public void keyPressed(KeyEvent emove)
   {
        if(b)
        {
            int keycode = emove.getKeyCode();
            if(keycode == 37)
            {
                ship.setLocation(ship.getX()-10, ship.getY());
                bullet.setLocation(bullet.getX()-10, bullet.getY());
                health.setLocation(health.getX()-10, health.getY());
            }
            else if(keycode == 39)
            {
                ship.setLocation(ship.getX()+10, ship.getY());
                bullet.setLocation(bullet.getX()+10, bullet.getY());
                health.setLocation(health.getX()+10, health.getY());
            }
            else if(keycode == 70)
            {
                if(b)
            {
                int bx = bullet.getX();
                ship.setFocusable(false);
                shootThread = new Thread(()->
                {   
                    for(int i=450;i>0;i-=10)
                    {
                        try
                        {
                            bullet.setBounds(bx,i,2,8);
                            if(bullet.getY()>=enemy.getY()&&bullet.getY()<=enemy.getY()+46&&bullet.getX()>=enemy.getX()&&bullet.getX()<=enemy.getX()+64)
                            {
                                if(enemy.l == 1)
                                {
                                    enemy.l2.setVisible(false);
                                    enemy.l = 0;
                                    break;
                                }
                                else if(enemy.l == 0)
                                {
                                    enemy.l1.setVisible(false);
//                                    sc = sc + 10;
//                                    score.setText("Score: " + sc);
                                    boom = false;
                                    break;
                                }
                            }
                            else if(bullet.getY()>=enemy_left.getY()&&bullet.getY()<=enemy_left.getY()+46&&bullet.getX()>=enemy_left.getX()&&bullet.getX()<=enemy_left.getX()+64)
                            {
                                if(enemy_left.l == 1)
                                {
                                    enemy_left.l2.setVisible(false);
                                    enemy_left.l = 0;
                                    break;
                                }
                                else if(enemy_left.l == 0)
                                {
                                    enemy_left.l1.setVisible(false);
//                                    sc = sc + 10;
//                                    score.setText("Score: " + sc);
                                    booml = false;
                                    break;
                                }
                            }
                            else if(bullet.getY()>=enemy_right.getY()&&bullet.getY()<=enemy_right.getY()+46&&bullet.getX()>=enemy_right.getX()&&bullet.getX()<=enemy_right.getX()+64)
                            {
                                if(enemy_right.l == 1)
                                {
                                    enemy_right.l2.setVisible(false);
                                    enemy_right.l = 0;
                                    break;
                                }
                                else if(enemy_right.l == 0)
                                {
                                    enemy_right.l1.setVisible(false);
//                                    sc = sc + 10;
//                                    score.setText("Score: " + sc);
                                    boomr = false;
                                    break;
                                }
                            }
                            Thread.sleep(20);
                        }
                        catch(Exception e1)
                        {
                        }   
                    }
                    bullet.setBounds(ship.getX()+31, ship.getY(), 2, 8);
                });
                shootThread.start();
            }


            }
        }
   }

   public void keyTyped(KeyEvent emove)
   {}

   public void keyReleased(KeyEvent emove)
   {}

   public void windowClosing(WindowEvent e)
    {
	   System.exit(0);
    }

   public static void main(String ar[])
   {
        starwars stws = new starwars();
   }
}
