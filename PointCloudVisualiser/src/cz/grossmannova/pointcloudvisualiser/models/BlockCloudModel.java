package cz.grossmannova.pointcloudvisualiser.models;

import java.util.List;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Pavla
 */
public class BlockCloudModel extends Model implements Cutable  {

    private int listId = -1;

    public BlockCloudModel(List<Block> blocksList) {
        super(blocksList, 1);
    }

    @Override
    public void draw() {
        if (isVisible()) {
              if (invalidate) {
                glInvalidateDisplayList();
            }
            GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 128f);

            if (listId == -1) {
                listId = GL11.glGenLists(1);
                GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
                GL11.glBegin(GL11.GL_QUADS);
                for (Block block : blocksList) {
                    if (doCut) {
                                if ((int) block.getPosition().getY() < cuttingPosition || (int) block.getPosition().getY() > cuttingPosition) {
                                    continue;
                                }
                            }
                    
                    GL11.glColor3f(block.getColor().getX(), block.getColor().getY(), block.getColor().getZ());
                    { // TOP
                        GL11.glNormal3f(0, 1, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(0, 1, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(0, 1, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(0, 1, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                    }
                    { // BOTTOM
                        GL11.glNormal3f(0, -1, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(0, -1, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(0, -1, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(0, -1, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() - 0.5f);
                    }
                    { // LEFT
                        GL11.glNormal3f(-1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(-1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(-1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(-1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                    }
                    { // RIGHT
                        GL11.glNormal3f(1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(1, 0, 0);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                    }
                    { // FRONT
                        GL11.glNormal3f(0, 0, 1);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(0, 0, 1);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(0, 0, 1);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                        GL11.glNormal3f(0, 0, 1);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() + block.getSize().z - 1 + 0.5f);
                    }
                    { // BACK
                        GL11.glNormal3f(0, 0, -1);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(0, 0, -1);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() - 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(0, 0, -1);
                        GL11.glVertex3f(block.getPosition().getX() - 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() - 0.5f);
                        GL11.glNormal3f(0, 0, -1);
                        GL11.glVertex3f(block.getPosition().getX() + block.getSize().x - 1 + 0.5f, block.getPosition().getY() + block.getSize().y - 1 + 0.5f, block.getPosition().getZ() - 0.5f);
                        //      }
                    }
                }
                GL11.glEnd();
                GL11.glEndList();
            } else {
                GL11.glCallList(listId);
            }
        }
    }
    
         @Override
    public void invalidateDisplayList() {
        invalidate = true;
    }

    public void glInvalidateDisplayList() {
        if (listId != -1) {
            GL11.glDeleteLists(listId, 1);
            listId = -1;
        }
        invalidate = false;
    }
}
