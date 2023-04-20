package kik.ikiki.kik;

import com.badlogic.gdx.graphics.Texture;

public class Button {
    private float x,y,w,h,depth=0;
    private Texture img;
    private Texture pushedImg;


    public Button(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    public Button(Texture img, float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.img = img;
        pushedImg=img;
    }

    public Button(Animation anm)
    {
        x=y=w=h=0;
        img = anm.frames[0];
        pushedImg = img;
        depth=0;
    }

    public Button(Texture txtr)
    {
        x=y=w=h=0;
        img = txtr;
        pushedImg = img;
        depth=0;
    }

    public Button(Texture txtr,float depth)
    {
        x=y=w=h=0;
        img = txtr;
        pushedImg = img;
        this.depth=depth;
    }


    public Button(float x, float y, float w, float h,float depth)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.depth = depth;
    }

    public Button(Texture img, float x, float y, float w, float h,float depth)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.img = img;
        this.depth = depth;
        pushedImg=img;
    }


    public boolean isPushed()
    {

        boolean res = false;
        for(int i = 0; i<10;i++)
            if(Mistener.onScreena[i])
            {
                if( ((Mistener.txa[i]>=x* Graph.mnX&&Mistener.txa[i]<=(x+w)* Graph.mnX)&&
                        (Mistener.tya[i]>=y* Graph.mnY&&Mistener.tya[i]<=(y+h)* Graph.mnY)) &&
                        ((Mistener.mxa[i]>=x* Graph.mnX&&Mistener.mxa[i]<=(x+w)* Graph.mnX)&&
                                (Mistener.mya[i]>=y* Graph.mnY&&Mistener.mya[i]<=(y+h)* Graph.mnY)))
                    res = true;
            }


        return res;
    }





    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getW()
    {
        return w;
    }

    public float getH()
    {
        return h;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setW(float w)
    {
        this.w = w;
    }

    public void setH(float h)
    {
        this.h = h;
    }

    public void setXY(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void setWH(float w, float h)
    {
        this.w = w;
        this.h = h;
    }

    public void setTexture(Texture txtr)
    {
        img = txtr;
    }

    public void setPushedTexture(Texture txtr)
    {
        pushedImg = txtr;
    }

    public void setDepth(float depth)
    {
        this.depth = depth;
    }

    public float getDepth()
    {
        return depth;
    }

    public Texture getTexture()
    {
        return isPushed()? pushedImg:img;
    }

    public void setXYWH(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }
}
