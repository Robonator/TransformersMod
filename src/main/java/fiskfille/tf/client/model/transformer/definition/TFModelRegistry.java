package fiskfille.tf.client.model.transformer.definition;

import fiskfille.tf.TransformerManager;
import fiskfille.tf.common.transformer.base.Transformer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class TFModelRegistry
{
    private static Map<Transformer, TransformerModel> models = new HashMap<Transformer, TransformerModel>();

    public static void registerModel(Transformer transformer, TransformerModel model)
    {
        models.put(transformer, model);
    }

    public static TransformerModel getModel(Transformer transformer)
    {
        return models.get(transformer);
    }

    public static void registerModels()
    {
        TFModelRegistry.registerModel(TransformerManager.transformerCloudtrap, new TFModelCloudtrap());
        TFModelRegistry.registerModel(TransformerManager.transformerPurge, new TFModelPurge());
        TFModelRegistry.registerModel(TransformerManager.transformerSkystrike, new TFModelSkystrike());
        TFModelRegistry.registerModel(TransformerManager.transformerSubwoofer, new TFModelSubwoofer());
        TFModelRegistry.registerModel(TransformerManager.transformerVurp, new TFModelVurp());
    }
}
