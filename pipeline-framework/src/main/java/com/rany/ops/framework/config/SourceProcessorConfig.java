package com.rany.ops.framework.config;

/**
 * Source Processor Config
 *
 * @author dick
 * @description Source Processor Config
 * @date 2021/12/20 11:18 下午
 * @email 18668485565@163.com
 */

public class SourceProcessorConfig extends ProcessorConfig {

    private ConvertConfig convertor;

    public ConvertConfig getConvertor() {
        return convertor;
    }

    public void setConvertor(ConvertConfig convertor) {
        this.convertor = convertor;
    }

    @Override
    public boolean validate() {
        if (null != convertor && !convertor.validate()) {
            return false;
        }
        return super.validate();
    }
}
