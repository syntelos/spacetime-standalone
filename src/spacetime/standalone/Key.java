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

/**
 * Signed X,Y,Z,T index key for collision list data structure
 * 
 * @see Spacetime
 */
public class Key
    extends Object
    implements java.lang.Comparable<Key>
{
    /**
     * 
     * @param ordinal Dimension ordinate 
     * @param resolution Index resolution
     */
    protected final static int IndexRep(float ordinal, float resolution){

        return (int)Math.floor(ordinal*resolution);
    }
    public final static Key For(Float x, Float y, Float z, Float t, float r){
        if (null == x || null == y)
            return new Key(0,0,0,0,r);
        else {
            if (null == t){
                if (null == z){
                    return new Key(x,y,0,0,r);
                }
                else {
                    return new Key(x,y,z,0,r);
                }
            }
            else if (null == z){
                return new Key(x,y,0,t,r);
            }
            else {
                return new Key(x,y,z,t,r);
            }
        }
    }

    /**
     * Index resolution values
     */
    protected final int x, y, z, t;
    /**
     * Index hash value
     */
    protected final int h;

    
    /**
     * @param x Ordinate first dimension
     * @param y Ordinate second dimension
     * @param z Ordinate third dimension
     * @param t Ordinate fourth dimension
     * @param r Index resolution operator
     */
    public Key(float x, float y, float z, float t, float r){
        super();
        this.x = IndexRep(x,r);
        this.y = IndexRep(y,r);
        this.z = IndexRep(z,r);
        this.t = IndexRep(t,r);

        int h = 0;

        hash:
        for (int d = 0; d < 4; d++){

            ordinal:
            switch(d){
            case 0:
                if (0 != this.x){

                    h = this.hash(d,h,this.x);
                    break ordinal;
                }
                else
                    break hash;

            case 1:
                if (0 != this.y){

                    h = this.hash(d,h,this.y);
                    break ordinal;
                }
                else
                    break hash;
            case 2:
                if (0 != this.z){

                    h = this.hash(d,h,this.z);
                    break ordinal;
                }
                else
                    break hash;
            case 3:
                if (0 != this.t){

                    h = this.hash(d,h,this.t);
                    break ordinal;
                }
                else
                    break hash;
            }
        }
        this.h = h;
    }


    /**
     * Hash performance in a small space improves with higher resolution
     */
    public int hash(int index, int hash, int ordinal){

        return (hash^ordinal);
    }
    public int hashCode(){
        return this.h;
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (that instanceof Key)
            return this.equals((Key)that);
        else
            return false;
    }
    public boolean equals(Key that){
        if (null == that)
            return false;
        else {
            return (this.x == that.x &&
                    this.y == that.y &&
                    this.z == that.z &&
                    this.t == that.t);
        }
    }
    /**
     * Sort relation
     */
    public int compareTo(Key that){

        if (null == that)
            return +1;
        else if (this.x > that.x){

            if (this.y > that.y){

                if (this.z > that.z){
                    /*
                     * +X,+Y,+Z,*T
                     */
                    return +1;
                }
                else if (this.t > that.t){
                    /*
                     * +X,+Y,-Z,+T
                     */
                    return +1;
                }
                else {
                    /*
                     * +X,+Y,-Z,-T (break)-
                     */
                    return -1;
                }
            }
            else if (this.z > that.z){
                /*
                 * +X,-Y,+Z,+T
                 */
                return +1;
            }
            else if (this.t > that.t){
                /*
                 * +X,-Y,-Z,+T (break)-
                 */
                return -1;
            }
            else {
                /*
                 * +X,-Y,-Z,-T
                 */
                return -1;
            }
        }
        else if (this.y > that.y){

            if (this.z > that.z){
                if (this.t > that.t){
                    /*
                     * -X,+Y,+Z,+T
                     */
                    return +1;
                }
                else {
                    /*
                     * -X,+Y,+Z,-T (break)-
                     */
                    return -1;
                }
            }
            else if (this.t > that.t){
                /*
                 * -X,+Y,-Z,+T (break)+
                 */
                return +1;
            }
            else {
                /*
                 * -X,+Y,-Z,-T
                 */
                return -1;
            }
        }
        else if (this.z > that.z){
            if (this.t > that.t){
                /*
                 * -X,-Y,+Z,+T (break)+
                 */
                return +1;
            }
            else {
                /*
                 * -X,-Y,+Z,-T
                 */
                return -1;
            }
        }
        else {
            /*
             * -X,-Y,-Z,*T
             */
            return -1;
        }
    }
}
