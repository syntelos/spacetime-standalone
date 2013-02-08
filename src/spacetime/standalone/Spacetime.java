/*
 * Spacetime Standalone
 * Copyright (C) 2013, John Pritchard.
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package spacetime.standalone;

import lxl.Index;

/**
 * In - memory properties of the space time object, and a collection
 * of space time objects.
 * 
 * @see Key
 * @see Face
 */
public class Spacetime<E extends Enum<E>>
    extends lxl.ArrayList<Spacetime<E>>
    implements spacetime.SpacetimeObject<java.lang.Float>,
               spacetime.SpacetimeFrame<java.lang.Float>,
               spacetime.Spacetime<java.lang.Float,Spacetime<E>>
{
    protected Spacetime frame;

    protected Float x, y, z, t;

    protected SpacetimeBounds bounds;

    protected SpacetimeMotion motion;
    /**
     * Multiply ordinals by resolution for index
     */
    protected float resolution = 1.0f;

    protected transient volatile Index<Key> stx;

    

    public Spacetime(){
        super();
    }
    public Spacetime(Float x, Float y, Float z){
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Spacetime(Float x, Float y, Float z, Float t){
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }
    protected Spacetime(Float x, Float y, Float z, Float t, float resolution){
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
        this.resolution = Math.abs(resolution);
    }
    protected Spacetime(float resolution){
        super();
        this.resolution = Math.abs(resolution);
    }


    public Spacetime getSpacetimeFrame(){
        return this.frame;
    }
    public Float getSpacetimeX(){
        return this.x;
    }
    public Float getSpacetimeY(){
        return this.y;
    }
    public Float getSpacetimeZ(){
        return this.z;
    }
    public Float getSpacetimeT(){
        return this.t;
    }
    public SpacetimeBounds getSpacetimeBounds(){
        return this.bounds;
    }
    public SpacetimeMotion getSpacetimeMotion(){
        return this.motion;
    }
    public void clear(){
        if (null != this.stx)
            this.stx.clear();

        super.clear();
    }
    /**
     * Indexed space time list
     */
    public <R extends Spacetime<E>> R get(Float x, Float y, Float z, Float t){

        final Key key = Key.For(x,y,z,t,this.resolution);

        final Index<Key> stx = this.stx();

        final int idx = stx.get(key);

        return (R)super.get(idx);
    }
    /**
     * Indexed space time list
     */
    public <R extends Spacetime<E>> R put(R p){

        final Key key = Key.For(x,y,z,t,this.resolution);

        final Index<Key> stx = this.stx();

        int idx = stx.get(key);
        if (-1 == idx){
            idx = super.add(p);
            stx.put(key,idx);

            this.restx();
        }
        else
            super.set(idx,p);

        return p;
    }
    /**
     * Enumerated list
     */
    public <R extends Spacetime<E>> R get(E en){

        return (R)super.get(en.ordinal());
    }
    /**
     * Enumerated list
     */
    public <R extends Spacetime<E>> R put(E en, R p){

        super.set(en.ordinal(),p);

        return p;
    }
    /**
     * @return List for iteration
     */
    public <R extends Spacetime<E>> java.lang.Iterable<R> iterable(){
        return (Iterable<R>)this;
    }
    public void restx(){
        Index<Key> stx = this.stx;
        if (null != stx){
            this.stx = stx.reindex();
        }
    }
    public <R extends Spacetime<E>> R clone(){

        R clone = (R)super.clone();

        Index<Key> stx = this.stx;
        if (null != stx){
            clone.stx = stx.clone();
        }

        return clone;
    }
    protected Index<Key> stx(){
        Index<Key> stx = this.stx;
        if (null == stx){
            stx = new Index();
            this.stx = stx;
        }
        return stx;
    }

    protected final static int Index(float ordinal, float resolution){

        return (int)Math.floor(ordinal*resolution);
    }
}
