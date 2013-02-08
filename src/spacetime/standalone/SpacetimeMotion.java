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


public class SpacetimeMotion
    extends Object
    implements spacetime.SpacetimeMotion<java.lang.Float>
{

    protected Float t, dx1, dx2, dx3, dy1, dy2, dy3, dz1, dz2, dz3;


    public SpacetimeMotion(){
        super();
    }


    public Float getSpacetimeT(){
        return this.t;
    }

    public Float getSpacetimeDeltaX(Float dt){

        throw new IllegalArgumentException();
    }
    public Float getSpacetimeDX1(){
        return this.dx1;
    }
    public Float getSpacetimeDX2(){
        return this.dx2;
    }
    public Float getSpacetimeDX3(){
        return this.dx3;
    }

    public Float getSpacetimeDeltaY(Float dt){

        throw new IllegalArgumentException();
    }
    public Float getSpacetimeDY1(){
        return this.dy1;
    }
    public Float getSpacetimeDY2(){
        return this.dy2;
    }
    public Float getSpacetimeDY3(){
        return this.dy3;
    }

    public Float getSpacetimeDeltaZ(Float dt){

        throw new IllegalArgumentException();
    }
    public Float getSpacetimeDZ1(){
        return this.dz1;
    }
    public Float getSpacetimeDZ2(){
        return this.dz2;
    }
    public Float getSpacetimeDZ3(){
        return this.dz3;
    }
}
