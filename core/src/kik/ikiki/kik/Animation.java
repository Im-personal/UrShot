package kik.ikiki.kik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Animation {
    Texture[] frames;
    float fps;
    float time = 0;
    boolean looped;
    public Animation(String folderpath, int len, float fps){
        frames = new Texture[len];
        for(int i = 0; i<frames.length;i++)
        {
            if(folderpath.contains("\\"))
                frames[i]=new Texture(folderpath+"\\frame"+i+".png");
            else
                frames[i]=new Texture(folderpath+"/frame"+i+".png");
        }
        this.fps = fps;
        looped=true;
    }

    public Animation(String folderpath, float fps)
    {
        FileHandle[] files = Gdx.files.internal(folderpath).list();
        frames = new Texture[files.length];
        for(int i = 0 ;i<frames.length;i++)
            frames[i]=new Texture(files[i]);
        looped = true;
        this.fps = fps;
    }

    public Animation(String folderpath, int len, String framename, String typename,float fps){
        frames = new Texture[len];
        for(int i = 0; i<frames.length;i++)
        {
            if(folderpath.contains("\\"))
                frames[i]=new Texture(folderpath+"\\"+framename+i+typename);
            else
                frames[i]=new Texture(folderpath+"/"+framename+i+typename);
        }
        this.fps = fps;
        looped=true;
    }

    public Animation(String folderpath, int len, String framename, String typename,int fps,boolean looped){
        frames = new Texture[len];
        for(int i = 0; i<frames.length;i++)
        {
            if(folderpath.contains("\\"))
                frames[i]=new Texture(folderpath+"\\"+framename+i+typename);
            else
                frames[i]=new Texture(folderpath+"/"+framename+i+typename);
        }
        this.fps = fps;
        this.looped=looped;
    }

    public void update()
    {
        time+= Gdx.graphics.getDeltaTime();
    }

    public Texture getFrame()
    {
        int framenum = (int) (time * fps);
        if(framenum>=0) {
            if (looped) {
                return frames[(framenum - (frames.length * (int) (framenum / frames.length)))];
            } else {
                if (framenum < frames.length) return frames[framenum];
                else
                    return frames[frames.length - 1];
            }
        }else{
          return frames[0];
        }
    }

    public Texture getFrame(float time)
    {
        int framenum = (int) (time * fps);
        if(looped) {
            return frames[(framenum - (frames.length * (int) (framenum / frames.length)))];
        }else
        {
            if(framenum<frames.length)return frames[framenum];
            else
                return frames[frames.length-1];
        }
    }

    void setPosition(float time)
    {
        this.time=time;
    }

    boolean finished()
    {
        int framenum = (int) (time * fps);
        return !(framenum<frames.length&&!looped);
    }

    void dispose()
    {
        for(int i = 0; i<frames.length;i++)frames[i].dispose();
    }

}
