package kik.ikiki.kik;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;


public class Graph extends ApplicationAdapter {

	static Preferences prefs;

	static SpriteBatch batch;
	Texture img;

	static int width = 0;
	static int height = 0;//1440 720

	static float mnX = 1;
	static float mnY = 1;



	static Texture img_shotoroom;
	Texture img_fridgebottom;
	Texture img_fridgemiddle;
	Texture img_fridgetop;
	Texture img_nfridgebottom;
	Texture img_nfridgemiddle;
	Texture img_nfridgetop;
	Texture img_blackpixel;
	Texture img_table;
	Texture img_basketblalroom;
	Texture img_hidenseekroom;
	Texture img_hidenseekroomforward;
	Texture img_sushotoroom;
	Texture img_spheroto;
	Texture img_ball;
	Texture img_blacksphere;
	Texture img_heart;
	Texture img_chatNewMessage;
	Texture img_chat;
	Texture img_talk;
	Texture img_shotoface;
	Texture img_firsteventbg;
	Texture img_molbert;
	Texture img_wall;
	Texture img_walldark;
	Texture[] img_colors;
	Texture img_shotofridge;
	Texture img_kibotofridge;
	Texture img_fridge;
	Texture img_fridge45;
	Texture img_fridgenorm;
	Texture img_chatbg;
	Texture img_wardrobe;
	Texture img_gift;


	Animation anm_shotosleep;
	Animation anm_shotosad;
	Animation anm_shotonotbad;
	Animation anm_shotook;
	Animation anm_shotohappy;
	Animation anm_shotonotification;
	Animation[] anm_shotoeat;
	Animation anm_shotocatched;
	Animation anm_shotorun;
	Animation anm_shotorunb;
	Animation anm_shotopose;
	Animation anm_shotofridgefall;

	static Button btn_feed;
	static Button btn_play;
	static Button btn_close;
	static Button btn_left;
	static Button btn_right;
	static Button btn_startevent;
	static Button btn_down;
	static Button btn_basketblal;
	static Button btn_hidenseek;
	static Button btn_sushoto;
	static Button btn_shotohide;
	static Button btn_chat;
	static Button btn_done;
	static Button[] btn_colors;
	static Button[] btn_costumes;
	static Button btn_lasttable;
	static Button btn_gift;
	static Button btn_box;


	static Sound snd_messagepop;
	static Sound snd_cancel;
	static Sound snd_cant;
	static Sound snd_eek;
	static Sound snd_inchappy;
	static Sound snd_nom;
	static Sound snd_showdrawing;

	static Music mus_drawing;

	BitmapFont font;


	static int shotostate=0;
	//0 - sleep
	//1 - sad
	//2 - notbad
	//3 - normal
	//4 - happy
	//5 - notification

	static boolean newCostume=false;
	static int costume = 3;
	boolean nfridge=false;
	static boolean pictureOnTheWall=false;
	static int[][] canvas = new int[32][32];
	static int choosenColor=0;
	static boolean nmessage=false;
	static boolean thereIsChat=true;
	static float hunger = 0f;
	static float happiness = 0f;
	static float eatingtime=0;
	static int gamechoosen = 0;
	static boolean gameStarted=false;
	static boolean isChatOpened= false;
	static boolean eventStarted=false;
	static float taste=0;
	static boolean showDrawing=false;
	static boolean thereIsGift = false;
	static class Food{
		Texture img;
		float x=0;
		float y=0;

		float like=1;

		public Food(Texture img)
		{
			this.img = img;
		}

		public Food(Texture img, float like, float x, float y)
		{
			this.img = img;
			this.like=like;
			this.x=x;
			this.y=y;
		}
		public Food(Texture img, float like)
		{
			this.img = img;
			this.like = like;
		}

	}

	static Food[] fridge;

	static ArrayList<Food> inventory = new ArrayList<>();

	static class Message{
		String msg;
		boolean play;

		public Message(String str){
			msg=str;
			play=false;
		}

		public Message(String str, boolean pl)
		{
			msg=str;
			play = pl;
		}

	}

	static ArrayList<Message> msges = new ArrayList<>();
//	int test=0;
	@Override
	public void create () {
		//	delay(5000);
		prefs = Gdx.app.getPreferences("kiki");

		//test = prefs.getInteger("please",1);
		//prefs.putInteger("please",2);
		//prefs.flush();
		wakeuptime = prefs.getLong("wakeup",0);
		shotoSleep = prefs.getBoolean("shotosleep",false);
		stage = prefs.getInteger("stage",0);
		pictureOnTheWall = prefs.getBoolean("picture",false);
		thereIsChat = prefs.getBoolean("thereischat",false);
		nfridge = prefs.getBoolean("anothercoolname",false);
		newCostume = prefs.getBoolean("newCostume",false);
		thereIsGift = prefs.getBoolean("thereIsGift",false);
		costume = prefs.getInteger("costume",0);
		if(pictureOnTheWall)
		{
			for(int i = 0; i<canvas.length;i++)
			for(int j = 0; j<canvas[i].length;j++)
				canvas[i][j] = prefs.getInteger("pic"+i+":"+j,0);
		}


		batch = new SpriteBatch();

		width=Gdx.graphics.getWidth();
		height=Gdx.graphics.getHeight();
		mnX = width/720f;
		mnY = height/1440f;
		Gdx.input.setInputProcessor(new Mistener());

		//TEXTURES
		img_shotoroom = new Texture(pictureOnTheWall?"shotoroomwitpicture.png":"shotoroom.png");
		img_fridgebottom = new Texture("fridgedown.png");
		img_fridgetop = new Texture("fridgeup.png");
		img_fridgemiddle = new Texture("fridgemid.png");
		img_nfridgebottom = new Texture("fridgedownnew.png");
		img_nfridgetop = new Texture("fridgeupnew.png");
		img_nfridgemiddle = new Texture("fridgemidnew.png");
		img_blackpixel = new Texture("blackpixel.png");
		img_table = new Texture("table.png");
		img_chatbg = new Texture("img_chatbg.png");
		img_wardrobe = new Texture("shkaf.png");


		FileHandle[] files = Gdx.files.internal("food").list();

		fridge = new Food[files.length];
		for(int i = 0; i<fridge.length;i++)
		{
			fridge[i] = new Food(new Texture(files[i]),(files[i].name().toCharArray()[0]-'0')/10f);
		}

		files = Gdx.files.internal("colors").list();
		img_colors = new Texture[files.length];
		for(int i = 0; i<img_colors.length;i++)
		img_colors[i] = new Texture(files[i]);

		img_basketblalroom = new Texture("basketblal.png");
		img_hidenseekroom = new Texture("img_hidenseek.png");
		img_hidenseekroomforward = new Texture("img_hidenseekfor.png");
		img_sushotoroom = new Texture("img_sushoto.png");
		img_ball = new Texture("img_ball.png");
		img_spheroto=new Texture("spheroto.png");
		img_blacksphere=new Texture("blacksphere.png");
		img_heart = new Texture("heart.png");
		img_chat = new Texture("chatbutton.png");
		img_chatNewMessage = new Texture("newMsg.png");
		img_talk = new Texture("usertalk.png");
		img_shotoface = new Texture("urshotlogo.png");
		img_firsteventbg = new Texture("paintbg.png");
		img_molbert = new Texture("albert.png");
		img_wall = new Texture("img_wall.png");
		img_walldark = new Texture("img_wallButDarker.png");
		img_shotofridge = new Texture("shotofridge.png");
		img_kibotofridge = new Texture("kiboto.png");
		img_fridge = new Texture("fridgedie.png");
		img_fridge45 = new Texture("fridge45.png");
		img_fridgenorm = new Texture("fridgedie2.png");
		img_gift = new Texture("drawing.png");


		//ANIMATIONS
		anm_shotosleep = new Animation("sleepen",1);
		anm_shotosad = new Animation("sad",1);
		anm_shotonotbad = new Animation("notbad",1);
		anm_shotook = new Animation("static",1);
		anm_shotohappy = new Animation("happy",1);
		anm_shotonotification = new Animation("notification",1);

		files = Gdx.files.internal("shotoeating").list();
		anm_shotoeat = new Animation[files.length];
		for(int i = 0; i< files.length;i++)
			anm_shotoeat[i] = new Animation(files[i].path(),10);


		anm_shotocatched = new Animation("happywitball",1);
		anm_shotorun = new Animation("shotorun",2);;
		anm_shotorunb = new Animation("shotorunb",2);;
		anm_shotopose = new Animation("pose",2);;
		anm_shotofridgefall = new Animation("shotofall",2);

		//BUTTONS
		btn_feed = new Button(new Texture("btn_feed.png"),423,1177,247,247,10);
		btn_play = new Button(new Texture("btn_play.png"),67,1177,247,247,10);
		btn_hidenseek = new Button(new Texture("btn_hidenseek.png"),67,1177,247,247,10);
		btn_basketblal = new Button(new Texture("btn_basketblal.png"),67,1177,247,247,10);
		btn_sushoto = new Button(new Texture("btn_sushoto.png"),67,1177,247,247,10);
		btn_close = new Button(new Texture("btn_close.png"),0,1177,247,247,10);
		btn_down = new Button(new Texture("btn_down.png"),0,1177,247,247,10);
		btn_left = new Button(new Texture("btn_left.png"),0,1177,247,247,10);
		btn_startevent = new Button(new Texture("btn_rightstart.png"),10);
		btn_right = new Button(new Texture("btn_right.png"),0,1177,247,247,10);
		btn_shotohide = new Button(anm_shotook);
		btn_chat = new Button(img_chat,10);
		btn_box = new Button(new Texture("box.png"));

		btn_colors = new Button[img_colors.length];
		for(int i = 0; i<btn_colors.length;i++)
			btn_colors[i] = new Button(img_colors[i],10);

		files = Gdx.files.internal("costume").list();
		btn_costumes = new Button[files.length];
		for(int i = 0; i<btn_costumes.length;i++)
			btn_costumes[i] = new Button(new Texture(files[i]));

		btn_done = new Button(new Texture("done.png"),10);
		btn_lasttable = new Button(new Texture("hpbdtable.png"));
		btn_gift = new Button(new Texture("gift.png"));
		//FONTS
		font = generateFont("calibri.ttf",2,2,Color.BLACK);

		//SOUNDS
		snd_cancel = Gdx.audio.newSound(Gdx.files.internal("sounds/cancel.mp3"));
		snd_cant = Gdx.audio.newSound(Gdx.files.internal("sounds/no.mp3"));
		snd_nom = Gdx.audio.newSound(Gdx.files.internal("sounds/nom.mp3"));
		snd_eek = Gdx.audio.newSound(Gdx.files.internal("sounds/eek.mp3"));
		snd_inchappy = Gdx.audio.newSound(Gdx.files.internal("sounds/happe.mp3"));
		snd_messagepop = Gdx.audio.newSound(Gdx.files.internal("sounds/messagepop.mp3"));
		snd_showdrawing = Gdx.audio.newSound(Gdx.files.internal("sounds/showdrawing.mp3"));

		//MUSICS
		mus_drawing = Gdx.audio.newMusic(Gdx.files.internal("sounds/draw.mp3"));

		if(stage==0&&!thereIsChat)
			box=true;

	}
	static boolean box=false;
	@Override
	public void render () {


		batch.begin();

		draw(batch);
		count();

		batch.end();





	}

	static float grow = 1;
	static int stage = 0;
	static boolean itWas=true;
	void draw(SpriteBatch b)
	{

		if(scene==0) {

			drawSprite(img_shotoroom, 0, screenMoveY, 720, 1440);

			if(pictureOnTheWall)
			{
				float x = 359;
				float y = 280;
				float w = 170;
				float h = 193;
				for(int i = 0; i<canvas.length;i++)
					for(int j = 0; j<canvas[i].length;j++)
					{
						if(canvas[i][j]!=0)
							drawSprite(img_colors[canvas[i][j]],x+(w/canvas.length*j),y+h/canvas[i].length*i+screenMoveY,w/canvas.length,h/canvas[i].length);
					}
			}

				switch (gamechoosen)
			{
				case 0:
					drawSprite(img_basketblalroom,0,-1440+screenMoveY,720,1440);
					if(!catched) {
						if(shotoX>ballx)
							drawSprite(anm_shotorun, shotoX - 200, 1294 - 400 - 1440 + screenMoveY, 400, 400);
						else
							drawSprite(anm_shotorunb, shotoX - 200, 1294 - 400 - 1440 + screenMoveY, 400, 400);



						drawSprite(img_ball, ballx, bally+screenMoveY-1440, 75, 75);
					} else
						drawSprite(anm_shotocatched,shotoX-200,1294-400-1440+screenMoveY,400,400);

					break;
				case 1:
					drawSprite(img_hidenseekroom,0,-1440+screenMoveY,720,1440);

					if(catched) {
						float ax = 0;


						switch (shotorunto)
						{
							case 0:
								ax = -17;

								break;
							case 1:
								ax = 117;
								break;
							case 2:
								ax = 455;
								break;
							case 3:
								ax = 325;
								break;
						}


						if(shotoX>ax)
							btn_shotohide.setTexture(anm_shotorun.getFrame());
						else
							btn_shotohide.setTexture(anm_shotorunb.getFrame());

					//	log((shotoX-ax)+","+(shotoY-ay));

					}
						else
					{
						btn_shotohide.setTexture(anm_shotook.getFrame(0));
					}
					btn_shotohide.setPushedTexture(btn_shotohide.getTexture());
					drawSprite(btn_shotohide,shotoX+(random(-5,5)),shotoY-1440+screenMoveY,400,400);



					drawSprite(img_hidenseekroomforward,0,-1440+screenMoveY,720,1440);




					break;
				case 2:
					drawSprite(img_sushotoroom,0,-1440+screenMoveY,720,1440);



					drawSprite(img_spheroto,shotoX-152/2f,shotoY-152/2f-1440+screenMoveY,152,152);
					for(int i = 0; i<4;i++)
						for(int j = 0; j<3;j++)
						drawSprite(img_blacksphere, 65+j*273+(i%2==0?-90:76), 324+i*250-1440+screenMoveY, 57, 57);



					drawSprite(img_heart,heartX-70+(float)(Math.sin(Math.PI/(360/2f)*System.currentTimeMillis())*20),1440-heartY-70-1440+screenMoveY,140,140);
					drawSprite(img_heart,heartX-70/2f+(float)(Math.cos(Math.PI/(360/2f)*System.currentTimeMillis())*20)+100,(1440-heartY*2)-70/2f-1440+screenMoveY,140/2f,140/2f);
					drawSprite(img_heart,heartX-70/2.5f+(float)(Math.sin(Math.PI/(360/2.5f)*System.currentTimeMillis())*20)-40,(1440-heartY*1.5f)-70/2.5f-1440+screenMoveY,140/2.5f,140/2.5f);



					break;
			}

			if(Graph.thereIsGift) {
				drawSprite(btn_gift,29,774+screenMoveY-35,170,183);

			}

			float shsz = 318 * grow;
			if(eatingtime<=0)
			switch (shotostate) {
				case 0:
				drawSprite(anm_shotosleep, 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
				break;

				case 1:
					drawSprite(anm_shotosad, 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
					break;

				case 2:
					drawSprite(anm_shotonotbad, 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
					break;

				case 3:
					drawSprite(anm_shotook, 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
					break;

				case 4:
					drawSprite(anm_shotohappy, 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
					break;
				case 5:
					drawSprite(anm_shotonotification, 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
					break;
			}
			else
			{

				drawSprite(anm_shotoeat[Math.round(taste*(anm_shotoeat.length-1))], 374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);
				anm_shotoeat[Math.round(taste*(anm_shotoeat.length-1))].update();
			}

			if(box)drawSprite(btn_box,104,526,510,536);

			if(costume!=0)
			drawSprite(btn_costumes[costume].getTexture(),374 - shsz, 939 - shsz * 2+screenMoveY, shsz * 2, shsz * 2);

			ttf=fridgeshift;
			if(toTheFridge&&Mistener.onScreen&&foodGrabbed==-1&&!feedShoto)ttf+=Mistener.dy;
			if(toTheFridge)
			{
				if(ttf>0)ttf=0;
				if(ttf+155*((int)(Graph.fridge.length/3f)+2)<1101)
					ttf -= ttf + 155 * ((int) (Graph.fridge.length / 3f) + 2) - 1101;
				}
			drawSprite(nfridge?img_nfridgetop:img_fridgetop,720+twoButtonShiftX-fridgeshiftx,ttf+screenMoveY,720,155);
			{
				int i = 0;
				for (;i<fridge.length/3;i++){
					drawSprite(nfridge?img_nfridgemiddle:img_fridgemiddle, 720+twoButtonShiftX-fridgeshiftx, ttf + 155 * (i+1)+screenMoveY, 720, 155);
					for(int j = 0; j<3;j++)
						if(i*3+j<fridge.length)
							if(i*3+j!=foodGrabbed)
								if(!inTheInv(fridge[i*3+j]))
							drawSprite(fridge[i*3+j].img,26+j*152+64*(j+1)+twoButtonShiftX+720-fridgeshiftx,4+155*(i+1)-50+ttf-53+screenMoveY,152,152);
				}
				drawSprite(nfridge?img_nfridgebottom:img_fridgebottom, 720+twoButtonShiftX-fridgeshiftx, ttf+155*(i+1)+screenMoveY, 720, 155);
			}

			drawSprite(img_table,720+twoButtonShiftX,1101+screenMoveY,720,206);

			for(int i = 0; i<inventory.size();i++)
				if(!feedShoto||(i!=foodGrabbed))
				drawSprite(inventory.get(i).img,inventory.get(i).x+720+twoButtonShiftX,inventory.get(i).y+screenMoveY,152,152);

			if(foodGrabbed!=-1&&!feedShoto)
			{
				drawSprite(fridge[foodGrabbed].img,Mistener.mx-foodgrabbedx,Mistener.my-foodgrabbedy,152,152);
			}

			if(feedShoto)
			{
				if(foodGrabbed!=-1)
					drawSprite(inventory.get(foodGrabbed).img,Mistener.mx-foodgrabbedx,Mistener.my-foodgrabbedy,152,152);
			}


			drawSprite(img_blackpixel,0,1294+screenMoveY,720,146);
			drawSprite(img_blackpixel,0,1294+screenMoveY-1440,720,146);




			drawSprite(btn_feed,twoButtonShiftX + 423 +playButShift*2, 1177+screenMoveY);
			//drawSprite(btn_play,twoButtonShiftX + 67+playButShift,btn_play.getY());
			if(readyToPlay) {
				drawSprite(btn_basketblal,twoButtonShiftX + 67+playButShift,1177-playButShift*1.5f+screenMoveY);
				drawSprite(btn_hidenseek,twoButtonShiftX + 67+playButShift-playButShift*1.2f,1177-playButShift*1.5f+screenMoveY);
				drawSprite(btn_sushoto,twoButtonShiftX + 67+playButShift+playButShift*1.2f,1177-playButShift*1.5f+screenMoveY);
				drawSprite(btn_close, twoButtonShiftX + 67+playButShift,1177);
			}else
			{
				if(playButShift>0)
				{
					drawSprite(btn_basketblal,twoButtonShiftX + 67+playButShift,1177-playButShift*1.5f+screenMoveY);
					drawSprite(btn_hidenseek,twoButtonShiftX + 67+playButShift-playButShift*1.2f,1177-playButShift*1.5f+screenMoveY);
					drawSprite(btn_sushoto,twoButtonShiftX + 67+playButShift+playButShift*1.2f,1177-playButShift*1.5f+screenMoveY);
				}
				else
				{
					drawSprite(btn_basketblal,-1000,1177-playButShift*1.5f+screenMoveY);
					drawSprite(btn_hidenseek,-1000,1177-playButShift*1.5f+screenMoveY);
					drawSprite(btn_sushoto,-1000,1177-playButShift*1.5f+screenMoveY);

				}
				drawSprite(btn_play,twoButtonShiftX + 67+playButShift,1177+screenMoveY);
				drawSprite(btn_close, twoButtonShiftX + 242 + 720, 1177+screenMoveY);
			}

			if(feedShoto)
			{
				drawSprite(btn_right,574+720+twoButtonShiftX-fridgeshiftx,screenMoveY,145,151);
			}
			else
			{
				drawSprite(btn_left,574+720+twoButtonShiftX-fridgeshiftx,screenMoveY,145,151);
			}

		}



		switch (stage){
			case 1:

				if(eventStarted)btn_startevent.setXYWH(-100,-100,0,0);

				drawSprite(img_firsteventbg,0,1440+screenMoveY,720,1440);
				drawSprite(anm_shotopose,15,752+1440+screenMoveY,455,408);
				drawSprite(img_molbert,258,590+1440+screenMoveY,474,1156);

				for(int i = 0; i<btn_colors.length;i++)
					drawSprite(btn_colors[i],26+657f/btn_colors.length*i,304+1440+screenMoveY,657f/btn_colors.length,102);


				float x = 316;
				float y = 727;
				float w = 334;
				float h = 433;

				for(int i = 0; i<canvas.length;i++)
					for(int j = 0; j<canvas[i].length;j++)
					{
						if(canvas[i][j]!=0)
							drawSprite(img_colors[canvas[i][j]],x+(w/canvas.length*j),y+h/canvas[i].length*i+1440+screenMoveY,w/canvas.length,h/canvas[i].length);
					}

				drawSprite(btn_done,271,1440+screenMoveY,275,282);


				break;

			case 2:

				float eyeX = -(shotoX-208);
				float eyeY = -(shotoY-698);
				drawSprite(img_wall,0,1440+screenMoveY,720,1440);



				drawSprite(img_walldark,0,1033+1440+screenMoveY+eyeY,720,400);

				if(catched) {



					if(choosenColor<19) {
						drawSprite(img_fridge45,ballx+eyeX,bally+1440+screenMoveY+eyeY,248,273);
						drawSprite(img_shotofridge, eyeX + shotoX + random(-1, 1), shotoY + 1440 + screenMoveY + random(-1, 1) + eyeY, 303, 301);

					}else {
						drawSprite(img_fridgenorm,ballx+eyeX,bally+1440+screenMoveY+eyeY,141,260);
						drawSprite(anm_shotofridgefall, 1961+eyeX, -727 + 1440 + screenMoveY + eyeY, -303, 301);
					}

					drawSprite(img_kibotofridge,eyeX+heartX,heartY+1440+screenMoveY+eyeY,289,250);
				}
				else
				{
					drawSprite(img_fridge,ballx+eyeX,bally+1440+screenMoveY+eyeY,260,141);
					drawSprite(anm_shotofridgefall,shotoX+eyeX,shotoY+1440+screenMoveY+eyeY,303,301);
					drawSprite(img_kibotofridge,heartX+eyeX,heartY+1440+screenMoveY+eyeY,289,250);
				}


				for(int i = 0; i<20;i++)
					drawSprite(img_walldark,288+74*i+eyeX,982-76*i+1440+screenMoveY+eyeY,74,1440);

				drawSprite(img_walldark,288+74*19+eyeX,982-76*19+1440+screenMoveY+eyeY,1000,1000);

				if(actionTimer>=0&&catched&&signnum!=-1) {
					Texture drawit;
					switch (signnum) {
						case 0:
							drawit = btn_left.getTexture();
							break;

						case 1:
							drawit = btn_right.getTexture();
							break;

						default:
							drawit = btn_close.getTexture();
							break;
					}


					drawSprite(drawit,203+random(-2,2),359+random(-2,2)+screenMoveY+1440,275,281);

				}


				drawSprite(img_blackpixel,0,1294+screenMoveY+1440,720,146);


				if(eventStarted) {
					drawSprite(btn_right, 423, 1177 + screenMoveY + 1440, 247, 247);
					drawSprite(btn_left, 64, 1177 + screenMoveY + 1440, 247, 247);
				}

				if(!catched&&actionTimer>=0)
				{
					drawSprite(img_blackpixel,720-(actionTimer*720f),0,720,1440);
					if(720-(actionTimer*720f)<=0)
					{
						catched = true;
						choosenColor=0;
						shotoX = 288-128;
						shotoY = 982-255;
					}

				}else{
					if(choosenColor==0)
						drawSprite(img_blackpixel,720-(actionTimer*720f),0,720,1440);

				}

				break;

			case 3:
				drawSprite(img_wardrobe,0,1440+screenMoveY,720,1440);
				for(int i = 0; i<btn_costumes.length;i++)
					drawSprite(btn_costumes[i],0+(720/(float)btn_costumes.length)*i,1043+1440+screenMoveY,(float)720/btn_costumes.length,195);

				drawSprite(anm_shotohappy,96,375+1440+screenMoveY,574,610);
				if(costume!=0)
				drawSprite(btn_costumes[costume].getTexture(),96,375+1440+screenMoveY,574,610);

				drawSprite(btn_done,163,81+1440+screenMoveY,396,407);

				break;


			case 4:
				drawSprite(btn_lasttable,0,1440+screenMoveY,720,1440);
				drawSprite(btn_done,165,53+1440+screenMoveY,356,363);

				if(eventStarted&&showDrawing)
					drawSprite(img_gift,0,1440+screenMoveY,720,1440);

				break;
		}

		if(thereIsChat)
		{
			drawSprite(btn_chat,560+twoButtonShiftX,16,126,143);

			if(isChatOpened)
			{logMistener();
				nmessage=false;
				drawSprite(img_chatbg,57,131,603,1035);

				for(int i = 0; i<msges.size();i++) {

					drawSprite(img_talk, 117, 172 + 154*i, 367+141, 117);
					drawSprite(img_shotoface,81,158+ 154*i,74,70);
					if(msges.get(i).play)
					{
						drawSprite(btn_startevent,483,145+ 154*i,164,191);
					}

					String[] s = msges.get(i).msg.split("\n");
					for(int j = 0; j<s.length;j++)
					drawString(s[j],166,183+ 154*i+48*j,font);

				}
			}else
			{
				if(!toTheFridge&&!(eventStarted))drawSprite(btn_startevent,-1000,-10000);
			}





			//log(eventStarted);

			if(Graph.thereIsGift) {
				if(Graph.showDrawing)
					drawSprite(img_gift, 0, 0, 720, 1440);
			}

		}





	}
	static boolean toTheFridge = false;
	static boolean feedShoto = false;
	float twoButtonShiftX = 0;
	float fridgeshiftx=0;
	static float fridgeshift=0;
	float ttf=0;
	static float screenMoveY=0;
	int scene=0;
	static int shotorunto = 0;

	static int foodGrabbed = -1;
	static float foodgrabbedx=0;
	static float foodgrabbedy=0;
	static boolean catched=true;
	static boolean shotoSleep=false;
	static boolean readyToPlay=false;
	static float playButShift;
	static float ballx=0,bally=0;
	static float ballxs=0,ballys=0;
	static float shotoX=750/2f;
	static float shotoY = -30;
	static float heartX = 0;
	static float heartY = 144045;
	static float actionTimer=0;
	static int signnum = -1;

	static float msgTimer=0;
	static int msgNum=0;
	static boolean sent=false;
	static long wakeuptime=0;
	static boolean shotoNotify = false;
	static int fail = 0;
	void count()
	{

		if(stage==1&&eventStarted&&!pictureOnTheWall&&Mistener.onScreen)
		{
			if(!mus_drawing.isPlaying())mus_drawing.play();
		}else{
			if(mus_drawing.isPlaying())mus_drawing.pause();
		}
			grow = (stage+1)/4f;
			if(!shotoSleep)
			if(!eventStarted)
			if(shotostate==2)msgNum=0;
			if(System.currentTimeMillis()>wakeuptime) {
				if(shotoSleep)
				{
					if(!Graph.eventStarted&&Graph.stage!=2)
					msges.clear();
					msgNum=0;
					shotoSleep = false;
					prefs.putBoolean("shotosleep",false);
					stage++;
					prefs.putInteger("stage",stage);
					prefs.putLong("wakeup",0);
					prefs.flush();
					wakeuptime=0;
				}

			}else shotoSleep=true;
			if(!shotoSleep)
			switch (stage)
			{
				case 0:
					if(thereIsChat)
					{
						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>2)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}

						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Наконец то я могу\nтебе писать!");
								break;
							case 1:
								if(!sent)
									newMessage("Я так рада!!!");
								break;
							case 3:
								if(!sent)
									newMessage("Я бы пообщалась но\nпосле всех игр");
								break;
							case 4:
								if(!sent)
									newMessage("Очень устала...");
								break;
							case 5:
								if(!sent)
									newMessage("Я отдохну пару минут!");
								break;
							case 6:
								if(!sent) {
									newMessage("..или часов ~.~");
									goToSleep(2);
								}
								break;
						}

					}
					break;

				case 1:
					if(pictureOnTheWall)
					{
						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>2)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}

						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("кывуеканпгнршгош");
								break;
							case 1:
								if(!sent)
									newMessage("Это так красиво!!! +0+");
								break;
							case 3:
								if(!sent)
									newMessage("Ты правда видишь \nменя такой?");
								break;
							case 4:
								if(!sent)
									newMessage("Ты наверное так устал \nот такого труда");
								break;

							case 5:
								if(!sent)
									newMessage("Делать такие \nшедевры");
								break;
							case 6:
								if(!sent)
									newMessage("очень утомительно");
								break;

							case 7:
								if(!sent)
									newMessage("Думаю, нам обоим \nстоит отдохнуть");
								break;

							case 8:
								if(!sent) {
									newMessage("Жду тебя снова!");
									goToSleep(2);
								}
								break;
						}

					}else if(shotostate==1)
					{

						float timeto = 0;


						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Я тут!");

								timeto = 1;
								break;
							case 1:
								if(!sent)
									newMessage("М-м, ни на что не\nнамекаю..");

								timeto = 1;
								break;

							case 2:
								if(!sent)
									newMessage("Но после хорошего \nсна ");

								timeto = 1;
								break;

							case 3:
								if(!sent)
									newMessage("надо бы восполнить\n силы\"\"");

								timeto = 0;
								break;

							case 4:
								if(!sent)
									newMessage("Например вкусной \nедой");
								break;

						}

						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>timeto)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}
					}else
						if(shotostate==5){

							if(eventStarted)
							{float timeto = 3;

								switch (msgNum)
								{
									case 0:
										if (!sent)
											newMessage("Начинай!");

										timeto = 30;
										break;

									case 1:
										if (!sent)
											newMessage("Ну как, получается?");

										timeto = 30;
										break;

									case 2:
										if (!sent)
											newMessage("Эй, ты еще долго?..");

										timeto = 30;
										break;
									case 3:
										if (!sent)
											newMessage("Я сейчас чихну -0-");

										timeto = 30;
										break;
									case 4:
										if (!sent)
											newMessage("Что же ты там так\n долго рисуешь?...");

										timeto = 30;
										break;
								}

								msgTimer += Gdx.graphics.getDeltaTime();
								if (msgTimer > timeto) {
									msgTimer = 0;
									msgNum++;
									sent = false;
								}
							}
							else {
								float timeto = 0;


								switch (msgNum) {
									case 0:
										if (!sent)
											newMessage("Странная просьба но...");

										timeto = 2;
										break;
									case 1:
										if (!sent)
											newMessage("можешь меня \nнарисовать?///");

										timeto = 1;
										break;

									case 2:
										if (!sent)
											newMessage("Мне интересно, как я \nвыгляжу ");

										timeto = 3;
										break;

									case 3:
										if (!sent)
											newMessage("в твоих глазах\nхехе~", true);

										timeto = 0;
										break;

								}

								msgTimer += Gdx.graphics.getDeltaTime();
								if (msgTimer > timeto) {
									msgTimer = 0;
									msgNum++;
									sent = false;
								}
							}
					}
					break;

				case 2:
					if(nfridge)
					{
						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>2)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}

						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Упс хаха\"\"");
								break;
							case 1:
								if(!sent)
									newMessage("Похоже у нас все это \nвремя был лифт...");
								break;
							case 3:
								if(!sent)
									newMessage("Ну, за то мы стали \nсильнее!");
								break;
							case 4:
								if(!sent)
									newMessage("Не думаешь?");
								break;

							case 5:
								if(!sent) {
									newMessage("Я рук не чувствую.. \nМы молодцы.. ");

								}
								break;
								case 6:
								if(!sent) {
									newMessage("надо бы немного.. \nпоспать");
									goToSleep(2);
								}
								break;

						}

					}else
					if(shotostate==1){


						float timeto = 0;


						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("М-м");

								timeto = 1;
								break;
							case 1:
								if(!sent)
									newMessage("Мне снился ты...");

								timeto = 1;
								break;

							case 2:
								if(!sent)
									newMessage("А еше вкусный чай \nс печеньем хехе");

								timeto = 2;
								break;

							case 3:
								if(!sent)
									newMessage("вот бы сейчас \nвоплотить");

								timeto = 2;
								break;

							case 4:
								if(!sent)
									newMessage("этот сон \nв реальность....");

								timeto = 2;
								break;





						}

						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>timeto)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}

					}else

						if(shotostate==5&&!eventStarted){
						float timeto = 0;


						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Эхе тут есть одна \nпроблемка..поможешь?");

								timeto = 1;
								break;
								case 1:
								if(!sent)
									newMessage("поможешь?");

								timeto = 1;
								break;
							case 2:
								if(!sent)
									newMessage("Надо поднять один \nхолодильник...");

								timeto = 2;
								break;

							case 3:
								if(!sent)
									newMessage("Зачем?");

								timeto = 2;
								break;
							case 4:
								if(!sent)
									newMessage("Ну как же..");

								timeto = 2;
								break;
							case 5:
								if(!sent)
									newMessage("Не стоять же \nему внизу :3",true);

								timeto = 0;
								break;

						}

						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>timeto)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}
					}
						else
							if(eventStarted)
							{

								switch (fail)
								{
									case 1:
										if(!sent)
											newMessage("Ай! Надо в следующий \nраз поосторожней..");
										break;

									case 2:
										if(!sent) {
											newMessage("Ничего страшного!");
											newMessage("попробуем еще раз!");
										}
									case 3:
										if(!sent)
											newMessage("Я не ударилась!!\nВсе в порядке!!");
										break;

									case 4:
										if(!sent) {
											newMessage("Этот холодильник не \nустоит под натиском");
											newMessage("двух булочек...");
										}
										break;

									case 5:
										if(!sent)
											newMessage("Не грусти, я верю в \nнас!У нас получится!");
										break;
								}

							}

					break;

				case 3:
					if(newCostume)
					{
						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>2)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}

						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Ваа тебе так \nнравится? >///<");
								break;
							case 1:
								if(!sent)
									newMessage("Я запомню хехе <3");
								break;
							case 3:
								if(!sent)
									newMessage("А теперь пора\nпередохнуть верно?");
								break;
							case 4:
								if(!sent) {
									newMessage("Спи сладенько, \nмилый!");
									goToSleep(2);
								}
								break;
						}

					}else if(shotostate==5&&!eventStarted){
						float timeto = 0;


						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Смотри что я нашла!!\n><");

								timeto = 1;
								break;
							case 1:
								if(!sent)
									newMessage("Кучу миленьких \nкостюмчиков!!");

								timeto = 2;
								break;

							case 2:
								if(!sent)
									newMessage("Хм...");

								timeto = 1;
								break;

							case 3:
								if(!sent)
									newMessage("как думаешь, \nчто мне идет?",true);

								timeto = 0;
								break;

						}

						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>timeto)
						{
							msgTimer=0;
							msgNum++;
							sent=false;
						}
					}
					break;

				case 4:
					if(happiness>=5){
						float timeto = 0;


						switch (msgNum)
						{
							case 0:
								if(!sent)
									newMessage("Эй, тут такое дело..\n~0~");

								timeto = 2;
								break;
							case 1:
								if(!sent)
									newMessage("У м-меня есть \nнебольшой подарочек");

								timeto = 2;
								break;

								case 2:
								if(!sent)
									newMessage("для тебя.. ///");

								timeto = 2;
								break;

							case 3:
								if(itWas)
								if(!sent) {
									newMessage("Надеюсь, тебе\nпонравится!!", true);
									msgNum++;
								}

								timeto = 3;
								break;


						}

						msgTimer+=Gdx.graphics.getDeltaTime();
						if(msgTimer>timeto)
						{
							if(msgNum==3)itWas=false;
							msgTimer=0;
							msgNum++;
							sent=false;



						}
					}
					break;

			}

			btn_chat.setTexture(nmessage?img_chatNewMessage:img_chat);
			btn_chat.setPushedTexture(nmessage?img_chatNewMessage:img_chat);

			if(gameStarted)
				{


				switch (gamechoosen)
				{
					case 0:
						anm_shotorun.update();
						anm_shotorunb.update();
						anm_shotocatched.update();
						if(Mistener.onScreen) {
							catched=false;
							ballx = (Mistener.mx/mnX)-75/2f;
							bally = (Mistener.my/mnY)-75/2f;
							ballxs=0;
							ballys=0;
						}else
						{
							ballx+=ballxs;
							bally+=ballys;
							ballys++;
							if(ballx<=0||ballx>=720) {
								ballxs=-ballxs;
							}
							if(ballx<=0)ballx=0;
							if(ballx>=720)ballx=720;
							//if(bally<=0)ballys=-ballys;
							if(bally>1294-75) {
								bally = 1294 - 75;
								ballys=-ballys/1.2f;
								ballxs/=2f;
								//ballys=0;
							}

							if(bally<0)
							{
								//ballys=-ballys;
								if(ballys<0)ballys=-ballys;
							}



						}

						if(!catched) {
							if (shotoX > ballx) shotoX -= 6;
							if (shotoX < ballx) shotoX += 6;
							if(!Mistener.onScreen)
								if(bally >= 1294 - 85&&Math.abs(shotoX-ballx)<7) {
									catched = true;
									happiness+=0.5f;
									snd_inchappy.play();
								}
						}
						break;


					case 1:
						if(catched){
							//325 601
							//455 298
							//117 -113
							//-17 498
							float ax = 0;
							float ay = 0;

							switch (shotorunto)
							{
								case 0:
									ax = -17;
									ay = 498;
									break;
								case 1:
									ax = 117;
									ay = -113;
									break;
								case 2:
									ax = 455;
									ay = 298;
									break;
								case 3:
									ax = 325;
									ay = 601;
									break;
							}

							if(shotoX<ax)shotoX+=20;
							if(shotoX>ax)shotoX-=20;
							if(shotoY<ay)shotoY+=20;
							if(shotoY>ay)shotoY-=20;
							if(eqError(shotoX,ax,20)&&eqError(shotoY,ay,20)){
								catched=false;
								shotoX=ax;
								shotoY=ay;

							}

						}
						break;

					case 2:
							heartY+=20;
							if(catched)
							{
								shotoX=Mistener.mx;
								shotoY=152/2f;
								ballys=0;
								ballxs=0;
							}
							else
							{
								shotoX+=ballxs;
								shotoY+=ballys;
								ballys++;
								if(shotoX<0) {
									shotoX = 0; ballxs=-ballxs;
								}
								if(shotoX>720) {
									shotoX = 720; ballxs=-ballxs;
								}

								if(shotoY>1440)
								{
									catched=true;
									heartX = shotoX;
									heartY = 0;//shotoY;
									happiness+=0.5f;
									snd_inchappy.play();
								}

								for(int i = 0; i<4;i++)
									for(int j = 0; j<3;j++)
									{//drawSprite(img_blacksphere, 65+j*273+(i%2==0?34:-64), 324+i*250-1440+screenMoveY, 57, 57);

										float balx=(65+j*273+(i%2==0?-90:76))+57f/2;
										float baly = 324+i*250+57f/2;

										float xx = Math.abs(shotoX-balx);
										float yy = Math.abs(shotoY-baly);

										float rads = 152f/2+57f/2;
										if(xx*xx+yy*yy<rads*rads)
										{
											float angle = getAngle(shotoX,shotoY,balx,baly);
											float mx = rotateX(0,0,1,0,angle);
											float my = rotateY(0,0,1,0,angle);
											float sum = ballxs+ballys;
											shotoX+=-mx*57/2;
											shotoY+=my*57/2;
											ballxs = -mx*sum/1.2f;
											ballys = my*sum/1.5f;

										}
									}

							}

						break;
				}




			}

			if(eatingtime>0)
			eatingtime-=Gdx.graphics.getDeltaTime();
			if(shotoSleep)shotostate=0;
			else
			{
				if(hunger<0.3)happiness=0.0f;

				if(happiness<=2.0f)shotostate=1;
				else if(happiness<=3.5f)shotostate=2;
				else if(happiness<=5.0f)shotostate=3;
				else shotostate=4;

				if(shotostate==4)
				{
					switch (stage)
					{
						case 0:
							if(!thereIsChat)
							shotostate=5;
							break;
						case 1:
							if(!pictureOnTheWall)
							shotostate=5;
							break;
						case 2:
							if(!nfridge)
								shotostate=5;
							break;
						case 3:
							if(!newCostume)
								shotostate=5;
							break;
					}
				}

			}


			if(gameStarted)
			{
				if(screenMoveY<1440)screenMoveY+=40;
			}
			else
			{
				if(screenMoveY>0)screenMoveY-=40;
			}

			if(eventStarted){
				if(screenMoveY>-1440)screenMoveY-=40;
			}else
			{
				if(screenMoveY<0)screenMoveY+=40;
			}

			if(scene==0) {
				anm_shotosleep.update();
				anm_shotonotification.update();
				anm_shotonotbad.update();
				anm_shotohappy.update();
				anm_shotook.update();
				anm_shotosad.update();
				if(eventStarted)
				{
					switch (stage)
					{
						case 1:
							anm_shotopose.update();
							break;

						case 2:
							anm_shotofridgefall.update();



							if(choosenColor<19) {
								if (catched) {//288+74*i,982-76*i+1440+screenMoveY
									float nx = 288 + 74 * choosenColor - 128;
									float ny = 982 - 76 * choosenColor - 255;
									if (shotoX > nx) shotoX -= 5;
									if (shotoX < nx) shotoX += 5;

									if (shotoY > ny) shotoY -= 5;
									if (shotoY < ny) shotoY += 5;

									ballx = shotoX + 22;
									bally = shotoY - 32;
									heartY = shotoY - 73;
									heartX = shotoX + 113;

									if (actionTimer > 0 && signnum == -1) {
										if (choosenColor <= 1) {
											signnum = 1;
										} else {
											signnum = Math.round(random(0, 2));
											if(signnum==1)Math.round(random(1,2));
										}
									}

									actionTimer += Gdx.graphics.getDeltaTime();

									if (choosenColor > 0)
										if (actionTimer >= 2) {
											if (signnum != -1)
												if (signnum > 1) {
													actionTimer = -2;
													catched = true;
													signnum = -1;
												} else {
													actionTimer = -2;
													signnum = -1;
													catched = false;
												}

										}


								} else {

									if (shotoY < 982 - 76 * 3) {
										shotoY += 20;
										shotoX -= 20;
										ballx -= 18;
										bally += 18;
										ballxs++;
									} else {
										if (ballxs > 0)
											ballxs--;
										shotoX -= ballxs;
										ballx -= ballxs / 2f;
										bally = shotoY + 125;
										actionTimer += Gdx.graphics.getDeltaTime();
									}


								}
							}else
							{
								float nx = 288 + 74 * choosenColor - 128;
								float ny = 982 - 76 * choosenColor - 255;


								if (shotoX > nx) shotoX -= 5;
								if (shotoX < nx) shotoX += 5;

								if (shotoY > ny) shotoY -= 5;
								if (shotoY < ny) shotoY += 5;


								ballx = nx+244;
								bally = ny-5;

								heartX = nx+271;
								heartY = ny+37;

								actionTimer += Gdx.graphics.getDeltaTime();
								if(actionTimer>=2){
									actionTimer=0;
									eventStarted=false;
									nfridge=true;
									msgNum=0;
								//	msges.clear();
									prefs.putBoolean("anothercoolname",true);
									prefs.flush();
								}

							}
							break;

					}
				}


				if(readyToPlay)
				{
					if(playButShift<190)playButShift+=19;
				//	playButShift=Mistener.dx;
				}else{
					if(playButShift>0)playButShift-=19;
				}

				if (toTheFridge) {
					if (twoButtonShiftX > -720) twoButtonShiftX -= 40;
					if(feedShoto) {
						if (fridgeshiftx < 585) fridgeshiftx += 45;
					}
					else
						if(fridgeshiftx>0)fridgeshiftx-=45;

						if(feedShoto)
						{
							for(int i = 0; i<inventory.size();i++)
								if(onElement(inventory.get(i).x,inventory.get(i).y,152,152))
								{
									foodGrabbed=i;
									foodgrabbedx=Mistener.tx-inventory.get(i).x;
									foodgrabbedy=Mistener.ty-inventory.get(i).y;
									break;
								}
						}
						else {
							if (Mistener.ty < 1101)
								for (int i = 0; i < fridge.length / 3; i++) {
									for (int j = 0; j < 3; j++)
										if (onElement(26 + j * 152 + 64 * (j + 1) + twoButtonShiftX + 720, 4 + 155 * (i + 1) - 50 + ttf-53, 152, 152))
											if (!inTheInv(fridge[i * 3 + j]) && Mistener.dx/mnX == 0 && Mistener.dy/mnY == 0) {

												foodGrabbed = i * 3 + j;
												foodgrabbedx = Mistener.tx - (26 + j * 152 + 64 * (j + 1) + twoButtonShiftX + 720);
												foodgrabbedy = Mistener.ty - (4 + 155 * (i + 1) - 50 + ttf-53);

												break;
											}
								}
						}
				if(!Mistener.onScreen)foodGrabbed=-1;
				} else {
					foodGrabbed=-1;
					feedShoto=false;
					if (twoButtonShiftX < 0) twoButtonShiftX += 40;
					if(fridgeshiftx>0)fridgeshiftx-=45;
					else
					{
						fridgeshift=0;
						if(inventory.size()>0)
						inventory.clear();
					}
				}



			}


	}


	@Override
	public void dispose () {

	}


	static int eyeX=0;
	static int eyeY=0;

	void drawSprite(Texture img, float x, float y,float w, float h){
		if(x+w>=0&&x<=720&&y+h>=0&&y<=1440)
			batch.draw(img, x*mnX, (1440-y-h)*mnY,w*mnX,h*mnY);
	}

	void drawSprite(Animation img, float x, float y,float w, float h){
		batch.draw(img.getFrame(), x*mnX, (1440-y-h)*mnY,w*mnX,h*mnY);
	}



	void drawSprite(Button but){
		if(but.isPushed())
			batch.draw(but.getTexture(), (but.getX()+ but.getDepth())*mnX,(1440-but.getH()-but.getY()-but.getDepth())*mnY,(but.getW()-but.getDepth()*2)*mnX,(but.getH()-but.getDepth()*2)*mnY);
		else
			batch.draw(but.getTexture(), but.getX()*mnX,(1440-but.getH()-but.getY())*mnY,but.getW()*mnX,but.getH()*mnY);
	}


	void drawSprite(Button but, float x, float y){
		but.setXY(x,y);
		if(but.isPushed())
			batch.draw(but.getTexture(), (but.getX()+ but.getDepth())*mnX,(1440-but.getH()-but.getY()-but.getDepth())*mnY,(but.getW()-but.getDepth()*2)*mnX,(but.getH()-but.getDepth()*2)*mnY);
		else
			batch.draw(but.getTexture(), but.getX()*mnX,(1440-but.getH()-but.getY())*mnY,but.getW()*mnX,but.getH()*mnY);
	}

	void drawSprite(Button but, float x, float y,float w, float h){
		but.setXYWH(x,y,w,h);
		if(but.isPushed())
			batch.draw(but.getTexture(), (but.getX()+ but.getDepth())*mnX,(1440-but.getH()-but.getY()-but.getDepth())*mnY,(but.getW()-but.getDepth()*2)*mnX,(but.getH()-but.getDepth()*2)*mnY);
		else
			batch.draw(but.getTexture(), but.getX()*mnX,(1440-but.getH()-but.getY())*mnY,but.getW()*mnX,but.getH()*mnY);
	}

	private BitmapFont generateFont(String fontName, float sizeX, float sizeY) {

		String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
				+ "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
				+ "1234567890.,:;_¡¿?\"'+-*/()[]={}<>!~^"+
				"abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJGKLMNOPQURSTUVWXYZ";


		// Configure font parameters
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.characters =RUSSIAN_CHARACTERS;
		parameter.size = 24;

		// Generate font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal(fontName) );
		BitmapFont font = generator.generateFont(parameter);
		font.getData().setScale(sizeX*mnX,sizeY*mnY);

		// Dispose resources
		generator.dispose();

		return font;
	}

	private BitmapFont generateFont(String fontName, float sizeX, float sizeY, Color color) {

		String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
				+ "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
				+ "1234567890.,:;_¡¿?\"'+-*/()[]={}<>!~"+
				"abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJGKLMNOPQURSTUVWXYZ";


		// Configure font parameters
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.characters =RUSSIAN_CHARACTERS;
		parameter.size = 24;

		// Generate font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal(fontName) );
		BitmapFont font = generator.generateFont(parameter);
		font.getData().setScale(sizeX*mnX,sizeY*mnY);
		font.setColor(color);
		// Dispose resources
		generator.dispose();

		return font;
	}

	private BitmapFont generateFont(String fontName, float sizeX, float sizeY, Color color,float borderWidth, Color bordercolor) {

		String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
				+ "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
				+ "1234567890.,:;_¡¿?\"'+-*/()[]={}<>!~^"+
				"abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJGKLMNOPQURSTUVWXYZ";


		// Configure font parameters
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.characters =RUSSIAN_CHARACTERS;
		parameter.size = 24;
		parameter.borderColor = bordercolor;
		parameter.borderWidth = borderWidth;
		// Generate font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal(fontName) );
		BitmapFont font = generator.generateFont(parameter);
		font.getData().setScale(sizeX*mnX,sizeY*mnY);
		font.setColor(color);
		// Dispose resources
		generator.dispose();

		return font;
	}

	void delay(long millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	String logMistener()
	{
		System.out.println(Mistener.tx+","+Mistener.ty+","+Mistener.dx+","+Mistener.dy);
		return Mistener.tx+","+Mistener.ty+","+Mistener.dx+","+Mistener.dy;
	}

	void logMistenerEye()
	{
		System.out.println((Mistener.tx+eyeX)+","+(Mistener.ty+eyeY)+","+Mistener.dx+","+Mistener.dy);
	}

	public void drawString(String str, float x, float y, BitmapFont font)
	{
		font.draw(batch,str,x*mnX,(1440f-y)*mnY);
	}

	void log()
	{
		System.out.println("DEBUG!!");
	}

	void log(int i)
	{
		System.out.println(i);
	}

	void log(String str)
	{
		System.out.println(str);
	}

	void log(boolean bool)
	{
		System.out.println(bool);
	}

	void log(char c)
	{
		System.out.println(c);
	}

	void log(float f)
	{
		System.out.println(f);
	}

	boolean onElement(float x, float y, float w, float h)
	{
		return (Mistener.tx>=x*mnX&&Mistener.tx<=(x+w)*mnX)&&(Mistener.ty>=y*mnY&&Mistener.ty<=(y+h)*mnY);
	}

	boolean onElementU(float x, float y, float w, float h)
	{
		return (Mistener.ux>=x&&Mistener.uy<=x+w)&&(Mistener.uy>=y&&Mistener.uy<=y+h);
	}

	boolean inTheInv(Food f)
	{
		for(int i = 0 ;i<inventory.size();i++)
			if(f.img==inventory.get(i).img)return true;
			return false;
	}

	float getAngle(float x0,float y0,float x1,float y1){
		if(x0==x1&&y0==y1)return 0;
		float cat1=x0-x1;
		float cat2=y0-y1;
		float tgns = cat1/cat2;
		if(y0-y1>=0)
			return (float)(180/3.14*Math.atan(tgns)+89);
		else
			return (float)(180/3.14*Math.atan(tgns)+180+89);
	}

	float rotateX(float x0,float y0,float x,float y,float a){
		a=(float)Math.PI/180*a;
		return (float)(Math.cos(a)*(x-x0)+Math.sin(a)*(y0-y)+x0);
	}

	float rotateY(float x0,float y0,float x,float y,float a){
		a=(float)Math.PI/180*a;
		return (float)(Math.cos(a)*(y-y0)+Math.sin(a)*(x-x0))+y0;
	}

	float random(int min, int max)
	{
		return (float)(min+(Math.random()*(max-min)));
	}

	boolean eqError(float a, float b, float error)
	{
		return (Math.abs(a-b)<=error);
	}

	void newMessage(String str)
	{
		msges.add(new Message(str));
		nmessage = true;
		sent=true;
		if(msges.size()>6)msges.remove(0);
		Graph.snd_messagepop.play();
	}

	void newMessage(String str, boolean bool)
	{
		msges.add(new Message(str,bool));
		nmessage=true;
		sent=false;
		if(msges.size()>6)msges.remove(0);
		Graph.snd_messagepop.play();
	}

	void goToSleep(int h){
	//	h=0.5f;
		wakeuptime = System.currentTimeMillis()+1000*60*30;
		prefs.putLong("wakeup",wakeuptime);
		prefs.putBoolean("shotosleep",true);
		prefs.flush();
		msgNum=0;
		happiness=0;
		hunger=0;
	}

}

