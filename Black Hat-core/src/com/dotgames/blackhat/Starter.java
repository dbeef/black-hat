package com.dotgames.blackhat;









import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Starter extends ApplicationAdapter {
	
	
	
	//
	int envyX2 =0;
	double timerEnvy2 = 0;
	int positionYbooting = 490; 
	Viewport viewport ;
	OrthographicCamera cam;
	
	boolean envyOff = false;
	double timerEnvyOff = 0;
	int currentBootingLine = 0;
	int currentBootingLetter = 0;
	double timerBooting = 0;
	private BitmapFont	bitmapFont;


	double interferePos[]; 
	String bootingProcedure[];
	SpriteBatch batch;
	Texture monitor;
	Texture ekran;
	Texture env;
	Texture interfere;
	//560 x 327
	float envyX = 0;
	float envyY = 50;
	
	double timerEnvy = 0;
	double timer = 0;
	double multiplier = 0;
	double timerInterfere = 0;
	int numberInter = 84;

	boolean isOn = true;
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		System.out.println("Working Directory ========= " +
	             System.getProperty("user.dir"));
		monitor = new Texture("monitor.png");
		ekran = new Texture("ekran.png");
		env = new Texture("env.png");
		interfere = new Texture("interfere.png");
		interferePos = new double[500];
		for(int a =0;a <500;a++){interferePos[a] = 0;}
		bootingProcedure = new String[24];
		for(int a=0;a < 24;a++){
        bootingProcedure[a] = new String();	
		}
		bootingProcedure[0] = "zone(0): 4096 pages.";
		bootingProcedure[1] = "zone(1): 61440 pages.";
		bootingProcedure[2] = "zone(2): 0 pages.";
		bootingProcedure[3] = "Kernel command line: BOOT_IMAGE=vmlinuz ide=nodma initrd=instroot.gz"; //root=/dev/r";
		bootingProcedure[4] = "am0 rw";
		bootingProcedure[5] = "ide_setup: ide=nodma : Prevented DMA";
		bootingProcedure[6] = "Initializing CPU#0";
		bootingProcedure[7] = "Detected 1615.700 MHz processor.";
		bootingProcedure[8] = "Console: colour VGA+ 80x25";
		bootingProcedure[9] = "Calibrating delay loop... 3217.81 BogoMIPS";
		bootingProcedure[10] = "Memory: 253900k/262144k available (1142k kernel code, 7792k reserved)";//, 350k data);//, 84k init, 0k highmem)";
		bootingProcedure[11] = "Dentry cache hash table entries: 32768 (order: 6, 262144 bytes)";
		bootingProcedure[12] = "Inode cache hash table entries: 16384 (order: 6, 262144 bytes)";
		bootingProcedure[13] = "Mount cache hash table entries: 512 (order: 6, 262144 bytes)";
		bootingProcedure[14] = "Buffer cache hash table entries: 16384 (order: 6, 262144 bytes)";
		bootingProcedure[15] = "Page-cache hash table entries: 65536 (order: 6, 262144 bytes)";
		bootingProcedure[16] = "CPU: Trace cache: 12k uops, L1 D cache: 8k";
		bootingProcedure[17] = "CPU: L2 cache: 512k";
		bootingProcedure[18] = "Intel machine check architecture supported.";
		bootingProcedure[19] = "Intel machine check reporting enabled on CPU#0.";
		bootingProcedure[20] = "CPU: Intel(R) Pentium(R) 4 CPU 1.60GHz stepping 08";
		bootingProcedure[21] = "Enabling fast FPU save and restore... done.";
		bootingProcedure[22] = "Enabling unmasked SIMD FPU exception support... done.";
		bootingProcedure[23] = "Checking 'hlt' instruction...";
		
		cam = new OrthographicCamera(960,540);
		cam.setToOrtho(false, 960, 540);
		cam.position.x = 480;
		cam.position.y  = 270;

		cam.update();
		
        viewport = new FitViewport(960,540);

		bitmapFont = new BitmapFont(Gdx.files.internal("clasconsole.fnt"),false);
		bitmapFont.setUseIntegerPositions(false);
		bitmapFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bitmapFont.setScale(1.0f);
		bitmapFont.setColor(0, 0, 0, 1);
		//
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("clacon.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 18;
		bitmapFont = generator.generateFont(parameter); // font size 12 pixels
		bitmapFont.setColor(Color.GREEN);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
	}

	@Override
	public void render () {
		//
		
		
		//
		if(Gdx.input.isTouched() == true){
		if(	isOn == true)isOn = false;
		else if(	isOn == false)isOn = true;
		
		}
		
		if(timer > 0.5f){
		timerInterfere += Gdx.graphics.getDeltaTime();
		if(timerInterfere > 0.01f)
		{
			interferePos[numberInter] = 0.1f;
			numberInter += 3;
			if(numberInter > 499)numberInter = 54;
		}
		}
		
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.setColor(1, 1, 1, (float)timer);
		batch.draw(monitor, 0, 0);
		if(timer == 1)
		if(timerEnvy == 1)//batch.draw(env, 184,116);
	batch.draw(env, 184, 116, (int)envyX, 327, 0,0, (int)envyX, 327 , false, false);
	//
		if(envyOff == true && envyX <= 0){
		drawBootingProcedure();	
		}
		
		if(envyOff == true){
		timerEnvy2 += Gdx.graphics.getDeltaTime();
		if(timerEnvy2 > 0.01f)
		{
			if(envyX > 0)envyX-=5;
		timerEnvy2 = 0;
		}
		}
//	drawBootingProcedure();
		//
		batch.draw(ekran, 84,46);
	
	
	//	if(timer > 0.5f){
		for(int a=0;a<500;a++){
			batch.setColor(1, 1, 1, (float)interferePos[a]);
			batch.draw(interfere,84,a);
		if(	interferePos[a] > 0)interferePos[a] -= Gdx.graphics.getDeltaTime()*0.2f;
		if(interferePos[a] < 0)interferePos[a] = 0;
		}
	//	}


	
		batch.end();
		
		if(timerEnvy == 1){
			timerEnvyOff += Gdx.graphics.getDeltaTime();
			if(timerEnvyOff > 2.5f)envyOff = true;
		}
		
		if(isOn == true){
					
			multiplier += Gdx.graphics.getDeltaTime();
		if(multiplier > 1)multiplier = 1;
		if(timer < 1)timer += Gdx.graphics.getDeltaTime() * multiplier;
		if(timer > 1)timer = 1;
		if(timer == 1)timerEnvy += Gdx.graphics.getDeltaTime();
		if(timerEnvy >= 1)timerEnvy = 1;
		if(timerEnvy == 1){

			if(envyX < 560 && envyOff == false)
				envyX += Gdx.graphics.getDeltaTime()*400;
				if(envyY < 327)envyY += Gdx.graphics.getDeltaTime()*100;
						
		}
		}
		if(isOn == false){
			if(timer > 0)timer -= Gdx.graphics.getDeltaTime();
			if(timer < 0)timer =0 ;
			if(timerEnvy > 0)timerEnvy -= Gdx.graphics.getDeltaTime();
			if(timerEnvy < 0)timerEnvy =0 ;
		
			
		}
		//System.out.println(timer);
	}
	public void drawBootingProcedure(){
		timerBooting += Gdx.graphics.getDeltaTime()*3;
		if(timerBooting > 0.01f){
		currentBootingLetter += 4;
		if(currentBootingLetter > bootingProcedure[currentBootingLine].length() ){
			currentBootingLetter =0;
			if(currentBootingLine < 21){currentBootingLine += 1;
		positionYbooting -= 20;
			}
		}
		
		}
		for(int a =0;a< currentBootingLine;a++){
			bitmapFont.draw(batch, bootingProcedure[a] , 90, 490 - 20*a);	
		}
		
		if(currentBootingLine < 21)
		bitmapFont.draw(batch, bootingProcedure[currentBootingLine].substring(0, currentBootingLetter) + "_", 90, positionYbooting);
		
		
									}
}
