package main;

public class QuadtreeNode<T> {


    private QuadtreeNode children[];
    private T elem;


    public QuadtreeNode() {

        this.children = new QuadtreeNode[4];
    }




    public void setElem(T elem)
    {
        this.elem = elem;
    }

    public boolean hasElem()
    {
        return this.elem != null;
    }
}
