package algorithm;

/**
 * @author kled
 * @description 嵌套集合模型 role hierarchy（limited hierarchy）
 * @version $Id: TestNestedSet.java, v 0.1 2018-09-03 16:16:47 kled Exp $
 */
public class TestNestedSet {

    private static RoleTree dahoAdmin;
    private static RoleTree ro;
    private static RoleTree providerAdmin;
    private static RoleTree bd;
    private static RoleTree user;
    private static RoleTree user_token;

    static {
        user = new RoleTree("User");
        user_token = new RoleTree("User_token");
        bd = new RoleTree(new RoleTree[]{user, user_token}, "BD");
        providerAdmin = new RoleTree(new RoleTree[]{bd}, "Provider Admin");
        ro = new RoleTree("RO Admin");
        dahoAdmin = new RoleTree(new RoleTree[]{ro,providerAdmin}, "DAHO Admin");
    }

    public void getNestedSetModel(){
        getRoleTreeRightval(dahoAdmin, 1);
        printRoleTree(dahoAdmin);
    }

    private Integer getRoleTreeRightval(RoleTree roleTree, Integer leftval){
        roleTree.setLeftval(leftval);
        if(roleTree.hasChild()){
            for (int i = 0; i < roleTree.childSize(); i++) {
                if(i == 0){
                    roleTree.getChilds()[i].setLeftval(leftval+1);
                } else {
                    roleTree.getChilds()[i].setLeftval(roleTree.getChilds()[i-1].getRightval()+1);
                }
                roleTree.getChilds()[i].setRightval(getRoleTreeRightval(roleTree.getChilds()[i], roleTree.getChilds()[i].getLeftval()));
            }
            roleTree.setRightval(roleTree.getChilds()[roleTree.childSize()-1].getRightval()+1);
            return roleTree.getRightval();
        } else {
            roleTree.setRightval(leftval+1);
        }
        return roleTree.getRightval();
    }

    private void printRoleTree(RoleTree roleTree){
        if(roleTree.hasChild()){
            for (int i = 0; i < roleTree.childSize(); i++) {
                printRoleTree(roleTree.getChilds()[i]);
            }
        }
        System.out.println("name: "+ roleTree.getName()+ ",leftval: "+ roleTree.getLeftval()+ ",rightval: "+roleTree.getRightval());
        return;
    }

    public static void main(String[] args) {
        TestNestedSet test = new TestNestedSet();
        test.getNestedSetModel();
    }

}

class RoleTree {

    private RoleTree parent;

    private Integer leftval;

    private Integer rightval;

    private RoleTree[] childs;

    private String name;

    public RoleTree(String name) {
        this.name = name;
    }

    public RoleTree(RoleTree[] childs, String name) {
        this.childs = childs;
        this.name = name;
    }

    public Integer childSize(){
        if(childs == null){
            return 0;
        }
        return childs.length;
    }

    public boolean hasChild(){
        return childs != null;
    }

    public Integer getLeftval() {
        return leftval;
    }

    public RoleTree setLeftval(Integer leftval) {
        this.leftval = leftval;
        return this;
    }

    public Integer getRightval() {
        return rightval;
    }

    public RoleTree setRightval(Integer rightval) {
        this.rightval = rightval;
        return this;
    }

    public RoleTree[] getChilds() {
        return childs;
    }

    public void setChilds(RoleTree[] childs) {
        this.childs = childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
