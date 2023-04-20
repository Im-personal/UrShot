package kik.ikiki.kik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Mistener implements InputProcessor {



    static int tx=0,ty=0,ux=0,uy=0,dx=0,dy=0,mx=0,my=0,difx=0,dify=0;
    static int[] txa=new int[10];
    static int[] tya=new int[10];
    static int[] uxa=new int[10];
    static int[] uya=new int[10];
    static int[] mxa=new int[10];
    static int[] mya=new int[10];
    static boolean onScreen = false;
    static boolean[] onScreena = new boolean[10];

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
     

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tx = screenX;
        ty = screenY;
        mx=tx;
        my=ty;
       txa[pointer] = screenX;
       tya[pointer] = screenY;
       mxa[pointer]=tx;
       mya[pointer]=ty;
       onScreena[pointer] = true;

        onScreen = true;

        if(Graph.btn_box.isPushed())Graph.box = false;

        float shsz = 318 * Graph.grow;
        if(onElement(tx/Graph.mnX,ty/Graph.mnY,374 - shsz, 939 - shsz * 2+Graph.screenMoveY, shsz * 2, shsz * 2))
        {
            if(Graph.stage==0&&Graph.shotostate==5)
            {
                Graph.thereIsChat=true;
                Graph.prefs.putBoolean("thereischat",true);
                Graph.prefs.flush();
            }
        }


        if(Graph.gameStarted&&Graph.gamechoosen==1&&!Graph.catched)
        {
            if(Graph.btn_shotohide.isPushed()){
                Graph.catched=true;
                Graph.happiness+=0.1f;
                Graph.snd_inchappy.play();
                int srtn = Graph.shotorunto;
                while (Graph.shotorunto==srtn)
                Graph.shotorunto= (int)(Math.random()*4f);

       //         System.out.println(Graph.shotorunto);
            }
        }

        if(Graph.eventStarted)
        {
            switch (Graph.stage){
                case 1:
                    float x = 316;
                    float y = 727;
                    float w = 334;
                    float h = 433;

                    for(int i = 0; i<Graph.canvas.length;i++)
                        for(int j = 0; j<Graph.canvas[i].length;j++)
                            if(onElement(mx,my,(x+(w/Graph.canvas.length*j))*Graph.mnX,(y+h/Graph.canvas[i].length*i)*Graph.mnY,(w/Graph.canvas.length)*Graph.mnX,(h/Graph.canvas[i].length)*Graph.mnY))
                        Graph.canvas[i][j] = Graph.choosenColor;
                    break;

                case 2:
                    if(Graph.choosenColor<19) {
                        if (Graph.btn_right.isPushed()) {
                            if (Graph.signnum == 1) {
                                Graph.actionTimer = -2;
                                Graph.signnum = -1;
                                Graph.choosenColor++;
                            } else {
                                Graph.actionTimer = -2;
                                Graph.signnum = -1;
                                Graph.catched = false;
                                Graph.fail++;
                                Graph.sent=false;
                                Graph.snd_eek.play();
                            }
                        }
                        if (Graph.btn_left.isPushed()) {
                            if (Graph.signnum == 0) {
                                Graph.actionTimer = -2;
                                Graph.signnum = -1;
                                Graph.choosenColor--;
                            } else {
                                Graph.actionTimer = -2;
                                Graph.signnum = -1;
                                Graph.catched = false;
                                Graph.snd_eek.play();
                                Graph.fail++;
                                Graph.sent=false;
                            }

                        }
                    }
                    break;
                case 3:
                    for(int i = 0; i<Graph.btn_costumes.length;i++)
                        if(Graph.btn_costumes[i].isPushed())Graph.costume=i;

                        if (Graph.btn_done.isPushed())
                        {
                            Graph.eventStarted = false;
                            Graph.newCostume = true;
                            Graph.prefs.putBoolean("newCostume",true);
                            Graph.prefs.putInteger("costume",Graph.costume);
                            Graph.prefs.flush();
                            Graph.msgNum = 0;
                        }
                    break;



            }



        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        ux = screenX;
        uy = screenY;


        uxa[pointer] = screenX;
        uya[pointer] = screenY;




        if(Graph.thereIsGift&&!Graph.eventStarted)
        {
            if(Graph.btn_gift.isPushed()&&!Graph.toTheFridge) {
                Graph.showDrawing = true;
                Graph.snd_showdrawing.play();
            }else Graph.showDrawing=false;
        }

        if(Graph.thereIsChat) {
            if(!Graph.isChatOpened)
            Graph.isChatOpened = Graph.btn_chat.isPushed();else Graph.isChatOpened=false;

            if(Graph.btn_startevent.isPushed())
            {
                Graph.readyToPlay=false;
                if(!(Graph.eventStarted&&Graph.stage==2))
                Graph.msges.clear();
                Graph.gameStarted=false;
                Graph.toTheFridge=false;
                Graph.eventStarted = true;
                Graph.choosenColor=0;

                Graph.msgNum=0;
                Graph.actionTimer=0;

                switch (Graph.stage)
                {
                    case 2:
                       Graph.shotoX = 288+74*Graph.choosenColor-128;
                       Graph.shotoY = 982-76*Graph.choosenColor-255;
                       Graph.actionTimer=200;
                        break;
                }

            }


            if(Graph.eventStarted)
            {
                switch (Graph.stage){
                    case 1:
                        for(int i = 0; i<Graph.btn_colors.length;i++)
                            if(Graph.btn_colors[i].isPushed()) {
                                Graph.choosenColor = i;
                                break;
                            }

                        if(Graph.btn_done.isPushed())
                        {
                            Graph.msgNum=0;
                            Graph.eventStarted=false;
                            Graph.pictureOnTheWall=true;
                            Graph.prefs.putBoolean("picture",true);
                            for(int i = 0; i<Graph.canvas.length;i++)
                                for(int j = 0; j<Graph.canvas[i].length;j++)
                                   Graph.prefs.putInteger("pic"+i+":"+j,Graph.canvas[i][j]);

                                Graph.prefs.flush();

                            Graph.img_shotoroom = new Texture("shotoroomwitpicture.png");
                        }
                        break;


                    case 4:
                        if(Graph.btn_lasttable.isPushed())
                        {
                            if(Graph.btn_done.isPushed())
                            {
                                Graph.eventStarted=false;
                                Graph.showDrawing=false;

                            }
                            else
                            {
                                Graph.showDrawing=!Graph.showDrawing;

                                Graph.thereIsGift=true;
                                Graph.prefs.putBoolean("thereIsGift",true);
                                Graph.prefs.flush();
                            }
                        }
                        break;

                }
            }


        }
        if(!Graph.gameStarted)
        {



            if(!Graph.toTheFridge&&Graph.readyToPlay)
            {
                if(Graph.btn_basketblal.isPushed())
                {
                    Graph.gamechoosen=0;
                    Graph.shotoX=750/2f;
                    Graph.catched=true;
                    Graph.gameStarted=true;
                }
                if(Graph.btn_hidenseek.isPushed())
                {
                    Graph.gamechoosen=1;

                    Graph.sent=false;
                    Graph.shotorunto=0;
                    Graph.shotoX=-17;
                    Graph.shotoY=498;
                    Graph.gameStarted=true;
                }
                if(Graph.btn_sushoto.isPushed())
                {
                    Graph.gamechoosen=2;
                    Graph.gameStarted=true;
                    Graph.catched=true;
                    Graph.heartY=12345;
                }
            }


            if (Graph.btn_feed.isPushed()) {
                Graph.toTheFridge = true;
            }
            if (Graph.btn_close.isPushed()) {
                if (Graph.toTheFridge) {
                    Graph.toTheFridge = false;
                    Graph.snd_cancel.play();
                }

                if (Graph.readyToPlay) {
                    Graph.readyToPlay = false;
                    Graph.gameStarted=false;
                }

            }

            if (Graph.btn_play.isPushed()&&Graph.hunger>=0.3f&&!Graph.shotoSleep) {
                Graph.readyToPlay = true;
            }else{
                if(Graph.btn_play.isPushed())
                Graph.snd_cant.play();
            }

            if (Graph.toTheFridge) {

                if (!Graph.feedShoto) {
                    if (Graph.foodGrabbed == -1) {
                        Graph.fridgeshift += Mistener.dy;

                        if (Graph.fridgeshift > 0) Graph.fridgeshift = 0;
                        if (Graph.fridgeshift + 155 * ((int) (Graph.fridge.length / 3f) + 2) < 1101) {

                            Graph.fridgeshift -= Graph.fridgeshift + 155 * ((int) (Graph.fridge.length / 3f) + 2) - 1101;

                        }
                    } else {
                        if (uy - Graph.foodgrabbedy + 152 >= 1101)//&&ux-Graph.foodgrabbedx>0&&ux-Graph.foodgrabbedx+152<720)
                        {
                            Graph.inventory.add(new Graph.Food(Graph.fridge[Graph.foodGrabbed].img, Graph.fridge[Graph.foodGrabbed].like, ux - Graph.foodgrabbedx, uy - Graph.foodgrabbedy));
                            //System.out.println("ye");
                        }
                    }
                }

                if (Graph.btn_left.isPushed()) {
                    Graph.feedShoto = true;
                    Graph.foodGrabbed = -1;
                }

                if (Graph.feedShoto) {
                    if (Graph.btn_right.isPushed()) {
                        Graph.feedShoto = false;
                        Graph.foodGrabbed = -1;
                    }

                    if (Graph.foodGrabbed != -1&&!Graph.shotoSleep) {//374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2
                        float shsz = 318 * Graph.grow;
                        if (ux >= (374 - shsz)*Graph.mnX && ux <= (374 - shsz+shsz * 2)*Graph.mnX && uy >= (939 - shsz * 2+Graph.screenMoveY)*Graph.mnY && uy <= (939 - shsz * 2+Graph.screenMoveY+shsz * 2)*Graph.mnY) {
                            Graph.eatingtime = 2;
                            Graph.hunger += 0.1f;
                            Graph.snd_nom.play();
                            if (Graph.happiness <= 2.0f)
                                Graph.happiness += 0.5f * Graph.inventory.get(Graph.foodGrabbed).like;
                            Graph.taste = Graph.inventory.get(Graph.foodGrabbed).like;
                            Graph.inventory.remove(Graph.foodGrabbed);
                            Graph.foodGrabbed = -1;
                        }
                    }

                }



            }
        }else
        {

            if(Graph.btn_close.isPushed())
            {
                if (Graph.readyToPlay) {
                    Graph.readyToPlay = false;
                    Graph.gameStarted=false;
                }
            }

            switch (Graph.gamechoosen)
            {
                case 0:
                    Graph.ballxs=difx;
                    Graph.ballys=dify;
                    Graph.catched=false;
                    break;

                case 2:
                    if(Graph.catched) {
                        Graph.shotoX = (mx)/Graph.mnX;
                        Graph.shotoY = 152/2f;
                        Graph.catched = false;

                    }
                    break;
            }

        }
        onScreen = false;

        dx = 0;
        dy = 0;
        mx=0;
        my=0;
        difx=0;
        dify=0;
        onScreena[pointer] = false;
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dx = screenX-tx;
        dy = screenY-ty;

        difx=screenX-mx;
        dify=screenY-my;

        mx = screenX;
        my = screenY;

        mxa[pointer]=mx;
        mya[pointer]=my;




        if(Graph.eventStarted)
        {
            switch (Graph.stage){
                case 1:
                    float x = 316;
                    float y = 727;
                    float w = 334;
                    float h = 433;

                    for(int i = 0; i<Graph.canvas.length;i++)
                        for(int j = 0; j<Graph.canvas[i].length;j++)
                            if(onElement(mx,my,(x+(w/Graph.canvas.length*j))*Graph.mnX,(y+h/Graph.canvas[i].length*i)*Graph.mnY,(w/Graph.canvas.length)*Graph.mnX,(h/Graph.canvas[i].length)*Graph.mnY)) {

                                //if(Graph.choosenColor==0)
                                {
                                    for(int k = i-1; k<i+1;k++)
                                        for(int l = j-1; l<j+1;l++)
                                            if(k>=0&&k<Graph.canvas.length&&l>=0&&l<Graph.canvas[k].length)
                                            Graph.canvas[k][l]=Graph.choosenColor;
                                }
                               // else
                                //Graph.canvas[i][j] = Graph.choosenColor;
                            }


                    break;
            }
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    boolean onElement(float tx, float ty, float x,float y, float w, float h)
    {
        return (tx>=x&&tx<=x+w&&ty>=y&&ty<=y+h);
    }

}
