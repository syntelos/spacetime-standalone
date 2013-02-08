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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

/**
 * This class exploits the array list implementation of {@link
 * Spacetime} to preserve the content of STL via a partially indexed
 * convention in the structure of produced and expected {@link
 * Spacetime} trees.
 * 
 * <pre>
 * 
 * face {
 * 
 *   face {
 *     n, a, b, c
 *   },
 *   ...,
 *   face {
 *     n, a, b, c
 *   }
 * }
 * </pre>
 * 
 * <p> The root {@link Spacetime} object contains a list of facet
 * nodes.  These nodes are not indexed. </p>
 * 
 * <p> Each STL facet is a list of vertices in order: normal, and then
 * first, second, and third vertices.  Within each face the normal is
 * not indexed, and the face vertices are indexed. </p>
 * 
 * @see Face
 */
public class STL<ST extends Face>
    extends Object
{
    public final static Charset ASCII = Charset.forName("US-ASCII");

    public enum Terms {
        solid, facet, normal, outer, loop, vertex, endloop, endfacet, endsolid;
    }
    protected final static Class[] CCtorParameters = {
        Float.class, Float.class, Float.class
    };



    public final Class<ST> clas;
    public final Constructor<ST> cctor;


    public STL(){
        this(Face.class);
    }
    public STL(Class clas){
        super();
        if (null != clas){
            this.clas = (Class<ST>)clas;
            try {
                this.cctor = this.clas.getConstructor(CCtorParameters);
            }
            catch (Exception exc){
                throw new IllegalStateException(this.clas.getName(),exc);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public ST create(){
        try {
            return this.clas.newInstance();
        }
        catch (Exception exc){
            throw new IllegalStateException(this.clas.getName(),exc);
        }
    }
    public ST create(float x, float y, float z){
        try {
            final Object[] args = {
                x,y,z
            };
            return this.cctor.newInstance(args);
        }
        catch (Exception exc){
            throw new IllegalStateException(this.clas.getName(),exc);
        }
    }
    public <R extends Spacetime> R createNormal(ST face){
        return face.createNormal();
    }
    public <R extends Spacetime> R createNormal(ST face, float x, float y, float z){

        return face.createNormal(x,y,z);
    }
    public <R extends Spacetime> R createVertex(ST face){
        return face.createVertex();
    }
    public <R extends Spacetime> R createVertex(ST face, float x, float y, float z){
        return face.createVertex(x,y,z);
    }

    public ST read(InputStream in)
        throws IOException
    {
        return this.read(new LineNumberReader(new InputStreamReader(in,ASCII)));
    }
    public ST read(LineNumberReader in)
        throws IOException
    {
        String lin;
        ST st = this.create();
        ST face = null;


        while (null != (lin = in.readLine())){
            StringTokenizer strtok = new StringTokenizer(lin," ");
            int count = strtok.countTokens();
            if (0 < count){
                Terms term = Terms.valueOf(strtok.nextToken());
                switch(term){
                case solid:
                    break;
                case facet:

                    face = this.create();

                    if (Terms.normal == Terms.valueOf(strtok.nextToken())){

                        float x = Float.parseFloat(strtok.nextToken());
                        float y = Float.parseFloat(strtok.nextToken());
                        float z = Float.parseFloat(strtok.nextToken());

                        face.normal(this.createNormal(face,x,y,z));
                    }
                    else
                        throw new IllegalStateException("facet missing normal");
                    break;
                case outer:
                    break;
                case vertex:{

                    float x = Float.parseFloat(strtok.nextToken());
                    float y = Float.parseFloat(strtok.nextToken());
                    float z = Float.parseFloat(strtok.nextToken());

                    face.vertex(this.createVertex(face,x,y,z));
                    break;
                }
                case endloop:

                    break;
                case endfacet:

                    st.face(face);
                    break;
                case endsolid:

                    return st;
                default:
                    throw new IllegalStateException(String.format("%s: %s",term.name(),lin));
                }
            }
        }
        return st;
    }
    public void write(OutputStream out, ST st)
        throws IOException
    {
        this.write(new PrintWriter(new OutputStreamWriter(out,ASCII)),st);
    }
    public void write(PrintWriter out, ST st)
        throws IOException
    {
        out.println("solid");

        final Iterable<Face> it = st.iterable(); // type coersion

        for (Face face: it){
            Spacetime n = face.n();
            Spacetime a = face.a();
            Spacetime b = face.b();
            Spacetime c = face.c();

            out.printf("  facet normal %f %f %f%n    outer loop%n",n.x,n.y,n.z);
            if (null != a){

                out.printf("      vertex %f %f %f%n",a.x,a.y,a.z);
            }
            if (null != b){

                out.printf("      vertex %f %f %f%n",b.x,b.y,b.z);
            }
            if (null != c){

                out.printf("      vertex %f %f %f%n",c.x,c.y,c.z);
            }

            out.println("    endloop");

            out.println("  endfacet");
        }
        out.println("endsolid");
        out.flush();
    }

    public enum Command {
        read, write;
    }
    private final static void usage(PrintStream out){
        out.println("Usage");
        out.println();
        out.println("    STL (read file)* (write file)* ");
        out.println();
        out.println("Description");
        out.println();
        out.println("    Test by running any sequence of reading and");
        out.println("    writing files.");
        out.println();
        System.exit(1);
    }
    public final static void main(String[] args){
        final int argc = args.length;
        try {
            final STL stl = new STL();

            Face st = new Face();

            if (1 < args.length){
                for (int arg = 0; arg < argc; arg++){
                    try {
                        Command cmd = Command.valueOf(args[arg]);
                        switch(cmd){
                        case read:
                            arg += 1;
                            if (arg < argc){
                                File fin = new File(args[arg]);

                                System.err.printf("read %s%n",fin.getPath());

                                final InputStream in = new FileInputStream(fin);
                                try {
                                    st = stl.read(in);
                                }
                                finally {
                                    in.close();
                                }
                            }
                            else {
                                usage(System.err);
                            }
                            break;

                        case write:
                            arg += 1;
                            if (arg < argc){
                                File fout = new File(args[arg]);

                                System.err.printf("write %s%n",fout.getPath());

                                final OutputStream out = new FileOutputStream(fout);
                                try {
                                    stl.write(out,st);
                                    out.flush();
                                }
                                finally {
                                    out.close();
                                }
                            }
                            else {
                                usage(System.err);
                            }
                            break;
                        }
                    }
                    catch (RuntimeException exc){
                        exc.printStackTrace();
                        System.exit(1);
                    }
                }
                System.exit(0);
            }
            else 
                usage(System.err);
        }
        catch (Exception exc){
            exc.printStackTrace();
            System.exit(1);
        }
    }
}
