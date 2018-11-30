package com.example.wenda.service;

import com.example.wenda.controller.LoginController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2018/11/29.
 */
@Service
public class SensitiveService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 默认敏感词替换符
     */
    private static final String DEFAULT_REPLACEMENT = "***";


    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while((lineTxt = bufferedReader.readLine() )!= null){
                addWord(lineTxt.trim());
            }

            reader.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }


    /**
     * 增加关键词
     * @param lineTxt
     */
    private void addWord(String lineTxt){
        TrieNode tempNode = rootNode;//指针指向根节点

        for(int i = 0; i < lineTxt.length(); ++i){
            //一次读取每个过滤的关键字
            Character c = lineTxt.charAt(i);

            // 过滤空格
            if (isSymbol(c)) {
                continue;
            }

            //查找原来父节点的子结点中是否含有该关键字
            TrieNode node = tempNode.getSubNode(c);

            //如果父节点没有该结点，那么创建该节点，并将关键字加进去
            if(node == null){
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }

            //如果父节点已经有该结点了，那么指针指向子结点（也就是找到的这个节点）
            tempNode = node;

            //如果是最后一个字了，那么end标签置为true
            if(i == lineTxt.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }

    }

    private class TrieNode{
        //是不是关键词的结尾
        private boolean end = false;

        //当前节点下的所有结点
        private Map<Character, TrieNode> subNodes = new HashMap<>();
        public void addSubNode(Character key, TrieNode node){
            subNodes.put(key,node);
        }

        TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeyWordEnd(){
            return end;
        }

        void setKeyWordEnd(boolean end){
            this.end = end;
        }
    }

    private TrieNode rootNode = new TrieNode();


    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String filter(String lineTxt){
        if(StringUtils.isBlank(lineTxt)){
            return lineTxt;
        }

        String replacement = DEFAULT_REPLACEMENT;
        StringBuilder result = new StringBuilder();

        TrieNode tempNode = rootNode;
        int begin = 0; // 回滚数
        int position = 0; // 当前比较的位置

        while (position < lineTxt.length()) {
            char c = lineTxt.charAt(position);
            // 空格直接跳过
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            // 当前位置的匹配结束
            if (tempNode == null) {
                // 以begin开始的字符串不存在敏感词
                result.append(lineTxt.charAt(begin));
                // 跳到下一个字符开始测试
                position = begin + 1;
                begin = position;
                // 回到树初始节点
                tempNode = rootNode;
            } else if (tempNode.isKeyWordEnd()) {
                // 发现敏感词， 从begin到position的位置用replacement替换掉
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }
        }

        result.append(lineTxt.substring(begin));

        return result.toString();
    }

    public static void main(String []args){
        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addWord("赌博");
        sensitiveService.addWord("色情");

        System.out.print(sensitiveService.filter("赌&&* #￥博可耻"));

    }
}
