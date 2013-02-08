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
 * A face is a list of vertices in order: normal, first, second, and
 * third vertices.  Within each face the normal is not indexed, and
 * the face vertices are indexed.
 * 
 * @see STL
 */
public class Face
    extends Spacetime
{
    /**
     * Out of band face normal is not indexed
     */
    public class Normal
        extends Spacetime
    {

        public Normal(){
            super();
        }
        public Normal(Float x, Float y, Float z){
            super(x, y, z);
        }
        public Normal(Float x, Float y, Float z, Float t){
            super(x, y, z, t);
        }
        protected Normal(Float x, Float y, Float z, Float t, float resolution){
            super(x, y, z, t,resolution);
        }
        protected Normal(float resolution){
            super(resolution);
        }
    }
    /**
     * Face vertices are ordered A, B, C
     */
    public class Vertex
        extends Spacetime
    {

        public Vertex(){
            super();
        }
        public Vertex(Float x, Float y, Float z){
            super(x, y, z);
        }
        public Vertex(Float x, Float y, Float z, Float t){
            super(x, y, z, t);
        }
        protected Vertex(Float x, Float y, Float z, Float t, float resolution){
            super(x, y, z, t,resolution);
        }
        protected Vertex(float resolution){
            super(resolution);
        }
    }
    /**
     * Face index operation
     */
    public enum FaceIndex {
        /*
         * Index centroid of face
         */
        IndexCentroid, 
        /*
         * Index face vertex A
         */
        IndexA, 
        /*
         * Index face vertex B
         */
        IndexB, 
        /*
         * Index face vertex C
         */
        IndexC, 
        /*
         * Index face vertices
         */
        IndexABC, 
        /*
         * Add face to list without indexing
         */
        FaceList;
    }


    protected Spacetime n;

    protected FaceIndex facex = FaceIndex.FaceList;


    public Face(){
        super();
    }
    public Face(FaceIndex facex){
        super();
        if (null != facex)
            this.facex = facex;
        else
            throw new IllegalArgumentException();
    }
    public Face(Float x, Float y, Float z){
        super(x, y, z);
    }
    public Face(Float x, Float y, Float z, Float t){
        super(x, y, z, t);
    }
    protected Face(Float x, Float y, Float z, Float t, float resolution){
        super(x, y, z, t,resolution);
    }
    protected Face(float resolution){
        super(resolution);
    }


    public final FaceIndex getType(){
        return this.facex;
    }
    public <R extends Spacetime> R createNormal(){
        return (R)(new Normal());
    }
    public <R extends Spacetime> R createNormal(float x, float y, float z){
        return (R)(new Normal(x,y,z));
    }
    public <R extends Spacetime> R createVertex(){
        return (R)(new Vertex());
    }
    public <R extends Spacetime> R createVertex(float x, float y, float z){
        return (R)(new Vertex(x,y,z));
    }
    public <R extends Spacetime> R n(){

        return (R)this.n;
    }
    public <R extends Spacetime> R a(){
        return (R)this.get(0);
    }
    public <R extends Spacetime> R b(){
        return (R)this.get(1);
    }
    public <R extends Spacetime> R c(){
        return (R)this.get(2);
    }
    public <R extends Spacetime> R normal(R n){

        return (R)(this.n = n);
    }
    public <R extends Spacetime> R a(R a){
        if (0 == this.size()){

            this.put(a);
            return a;
        }
        else
            return (R)this.update(0,a);
    }
    public <R extends Spacetime> R b(R b){
        if (1 == this.size()){

            this.put(b);
            return b;
        }
        else
            return (R)this.update(1,b);
    }
    public <R extends Spacetime> R c(R c){
        if (2 == this.size()){

            this.put(c);
            return c;
        }
        else
            return (R)this.update(2,c);
    }
    public <R extends Spacetime> R vertex(R v){
        final int end = this.size();
        if (0 <= end && 3 > end){

            this.put(v);
            return v;
        }
        else
            throw new UnsupportedOperationException();
    }
    public <R extends Face> R face(R f){

        switch(this.facex){
        case IndexCentroid:
            throw new UnsupportedOperationException("alt.todo");
        case IndexA:
            this.put(f.a());
            break;
        case IndexB:
            this.put(f.b());
            break;
        case IndexC:
            this.put(f.c());
            break;
        case IndexABC:
            this.put(f.a());
            this.put(f.b());
            this.put(f.c());
            break;
        case FaceList:
            this.add(f);
            break;
        default:
            throw new IllegalStateException(this.facex.name());
        }
        return f;
    }
}
