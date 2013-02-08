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


public class SpacetimeBounds
    extends Object
    implements spacetime.SpacetimeBounds<java.lang.Float>
{

    protected Float x0, x1, y0, y1, z0, z1, t0, t1;


    public SpacetimeBounds(){
        super();
    }


    public Float getSpacetimeXmin(){
        return this.x0;
    }
    public Float getSpacetimeXmax(){
        return this.x1;
    }

    public Float getSpacetimeYmin(){
        return this.y0;
    }
    public Float getSpacetimeYmax(){
        return this.y1;
    }

    public Float getSpacetimeZmin(){
        return this.z0;
    }
    public Float getSpacetimeZmax(){
        return this.z1;
    }

    public Float getSpacetimeTmin(){
        return this.t0;
    }
    public Float getSpacetimeTmax(){
        return this.t1;
    }
}
