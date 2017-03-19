package com.dotgames.blackhat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
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

	public enum ShaderSelection{
		Default,
		Ambiant,
		Light,
		Final
	};

	
	//used for drawing

	private OrthographicCamera cam2d;
	private boolean	lightMove = false;
	private boolean lightOscillate = false;
	private Texture light;
	private FrameBuffer fbo;

	
	//out different shaders. currentShader is just a pointer to the 4 others
	private ShaderSelection	shaderSelection = ShaderSelection.Default;
	private ShaderProgram currentShader;
	private ShaderProgram defaultShader;
	private ShaderProgram ambientShader;
	private ShaderProgram lightShader;
	private ShaderProgram finalShader;
	
	//values passed to the shader
	public static final float ambientIntensity = .7f;
	public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);

	//used to make the light flicker
	public float zAngle;
	public static final float zSpeed = 15.0f;
	public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;
	
	//read our shader files
	final String vertexShader = new FileHandle("data/vertexShader.glsl").readString();
	final String defaultPixelShader = new FileHandle("data/defaultPixelShader.glsl").readString();
	final String ambientPixelShader = new FileHandle("data/ambientPixelShader.glsl").readString();
	final String lightPixelShader =  new FileHandle("data/lightPixelShader.glsl").readString();
	final String finalPixelShader =  new FileHandle("data/pixelShader.glsl").readString();
	
	//change the shader selection
	public void setShader(ShaderSelection ss){
		shaderSelection = ss;
		
		if(ss == ShaderSelection.Final){
			currentShader = finalShader;
		}
		else if(ss == ShaderSelection.Ambiant){
			currentShader = ambientShader;
		}
		else if(ss == ShaderSelection.Light){
			currentShader = lightShader;
		}
		else{
			ss = ShaderSelection.Default;
			currentShader = defaultShader;
		}
	}

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
//
		ShaderProgram.pedantic = false;
		defaultShader = new ShaderProgram(vertexShader, defaultPixelShader);
		ambientShader = new ShaderProgram(vertexShader, ambientPixelShader);
		lightShader = new ShaderProgram(vertexShader, lightPixelShader);
		finalShader = new ShaderProgram(vertexShader, finalPixelShader);
		setShader(shaderSelection);

monitor = new Texture("monitor.png");

		ambientShader.begin();
		ambientShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
				ambientColor.z, ambientIntensity);
		ambientShader.end();
		

		lightShader.begin();
		lightShader.setUniformi("u_lightmap", 1);
		lightShader.end();
		
		finalShader.begin();
		finalShader.setUniformi("u_lightmap", 1);
		finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
				ambientColor.z, ambientIntensity);
		finalShader.end();
		
		
		//declare all stuff we need to draw

		light = new Texture("data/light.png");

		//input processing
		Gdx.input.setInputProcessor(new InputAdapter() {
			
			public boolean scrolled(int amount){
				cam.zoom += (float)amount * 0.04f;
				cam.update();
				return false;
			}
			public boolean keyUp(int keycode) {
				if(keycode == Keys.LEFT){
					cam.translate(-1.0f, 0.0f);
					cam.update();
				}
				else if(keycode == Keys.RIGHT){
					cam.translate(1.0f, 0.0f);
					cam.update();
				}
				else if(keycode == Keys.UP){
					cam.translate(0.0f, 1.0f);
					cam.update();
				}
				else if(keycode == Keys.DOWN){
					cam.translate(0.0f, -1.0f);
					cam.update();
				}
				else if(keycode == Keys.NUM_1){
					setShader(ShaderSelection.Default);
				}
				else if(keycode == Keys.NUM_2){
					setShader(ShaderSelection.Ambiant);
				}
				else if(keycode == Keys.NUM_3){
					setShader(ShaderSelection.Light);
				}
				else if(keycode == Keys.NUM_4){
					setShader(ShaderSelection.Final);
				}
				else if(keycode == Keys.SPACE){
					lightOscillate = !lightOscillate;
				}
				
				return false;
			}
			public boolean touchUp(int x, int y, int pointer, int button) {
				lightMove = !lightMove;
				return false;
			}
		});

		//
	}

	@Override
	public void resize(final int width, final int height) {
		cam = new OrthographicCamera(960,580);
		cam.position.set(480,270,0);
		//cam.zoom = 20;
	cam.update();
		
		cam2d = new OrthographicCamera(960, 580);
		cam2d.position.set(480,330, 0.0f);
		cam2d.update();


		fbo = new FrameBuffer(Format.RGBA8888, 960, 580, false);
		 
		lightShader.begin();
		lightShader.setUniformf("resolution", 960, 580);
		lightShader.end();

		finalShader.begin();
		finalShader.setUniformf("resolution", 960, 580);
		finalShader.end();
	}

	@Override
	public void render () {
		//
		final float dt = Gdx.graphics.getRawDeltaTime();
		

		zAngle += dt * zSpeed;
		while(zAngle > PI2)
			zAngle -= PI2;
Vector3 vec = new Vector3();
vec.x = Gdx.input.getX();
vec.y = Gdx.input.getY();
 cam.unproject(vec);
		
		//draw the light to the FBO
		fbo.begin();
		batch.setProjectionMatrix(cam.combined);
		batch.setShader(defaultShader);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		float lightSize = lightOscillate? (10000.75f + 0.25f * (float)Math.sin(zAngle) + .2f*MathUtils.random()):5.0f;
		batch.draw(light, vec.x,vec.y, lightSize, lightSize);
		batch.end();
		fbo.end();
		
		//draw the actual scene
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.setShader(currentShader);
		batch.begin();
		fbo.getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit
		light.bind(0); //we force the binding of a texture on first texture unit to avoid artefacts
					   //this is because our default and ambiant shader dont use multi texturing...
					   //youc can basically bind anything, it doesnt matter



		

		updateTimers();
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
		
//		batch.setProjectionMatrix(cam.combined);

//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();

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
		

		batch.setProjectionMatrix(cam2d.combined);
		batch.setShader(defaultShader);
		batch.begin();
		

		float x = 0.0f;
		bitmapFont.setColor(shaderSelection==ShaderSelection.Default?Color.YELLOW:Color.WHITE);
		x += bitmapFont.draw(batch, "1=Default Shader", x, cam2d.viewportHeight).width;
		bitmapFont.setColor(shaderSelection==ShaderSelection.Ambiant?Color.YELLOW:Color.WHITE);
		x += bitmapFont.draw(batch, " 2=Ambiant Light", x, cam2d.viewportHeight).width;
		bitmapFont.setColor(shaderSelection==ShaderSelection.Light?Color.YELLOW:Color.WHITE);
		x += bitmapFont.draw(batch, " 3=Light Shader", x, cam2d.viewportHeight).width;
		bitmapFont.setColor(shaderSelection==ShaderSelection.Final?Color.YELLOW:Color.WHITE);
		x += bitmapFont.draw(batch, " 4=Final Shader", x, cam2d.viewportHeight).width;
		x = 0.0f;
		bitmapFont.setColor(lightMove?Color.YELLOW:Color.WHITE);
		x += bitmapFont.draw(batch, "click=light control (" +lightMove+ ")", x, cam2d.viewportHeight-bitmapFont.getLineHeight()).width;
		bitmapFont.setColor(lightOscillate?Color.YELLOW:Color.WHITE);
		x += bitmapFont.draw(batch, " space=light flicker (" +lightOscillate+ ")", x, cam2d.viewportHeight-bitmapFont.getLineHeight()).width;
		x = 0.0f;
		bitmapFont.setColor(Color.WHITE);
		x += bitmapFont.draw(batch, Gdx.graphics.getFramesPerSecond() + " fps", x, cam2d.viewportHeight-bitmapFont.getLineHeight()*2.0f).width;
		batch.end();
		
		
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
public void updateTimers(){
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
						}

}
