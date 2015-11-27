/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fplearning.jhh;

import unalcol.instance.InstanceService;

public class ProgramInstance implements InstanceService<ProgramTree>
{
    @Override
    public ProgramTree get(ProgramTree t)
    {
        String[] functor =
        {
            "max", "s"
        };
        int[] arityFun =
        {
            2, 1
        };
        String[] terminal =
        {
            "0", "X", "Y"
        };
        
        Generator g = new Generator(functor, arityFun, terminal, 4, 10);
        
        return g.generateValidProgram();
    }

    @Override
    public Object owner()
    {
        return ProgramTree.class;
    }
}
