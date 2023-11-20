package com.jcy.utils;

/**
 * @author Clinton Begin
 */
public class GenericTokenParser {

  private final String openToken;  //开始标记
  private final String closeToken;  //结束标记
  private final TokenHandler handler;  //标记处理器

  public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
    this.openToken = openToken;
    this.closeToken = closeToken;
    this.handler = handler;
  }

  /**
   * 解析${}和#{}
   * @param text
   * @return
   * 该方法主要实现了配置文件，脚本等片段中占位符的解析，处理工作，并返回最终需要的数据
   * 其中，解析工作由该方法完成，处理工作是由处理器handler和handleToken()方法来实现
   */
  public String parse(String text) {
    // 验证参数问题，如果是null，就返回空字符串
    if (text == null || text.isEmpty()) {
      return "";
    }
    //  下面继续验证是否包含开始标签，如果不包含，默认不是占位符，直接原样返回即可，否则继续执行
    int start = text.indexOf(openToken);
    if (start == -1) {
      return text;
    }

    //把text转成字符串数组src，并且定义默认偏移量offset=0，存储最终需要返回字符串的变量builder
    //text变量中占位符对应的变量名expression，判断start是否大于-1（即text中是否存在openToken），如果存在就执行
    char[] src = text.toCharArray();
    int offset = 0;
    final StringBuilder builder = new StringBuilder();
    StringBuilder expression = null;
    do {
      if (start > 0 && src[start - 1] == '\\') {
        // this open token is escaped. remove the backslash and continue.
        builder.append(src, offset, start - offset - 1).append(openToken);
        offset = start + openToken.length();
      } else {
        // found open token. let's search close token.
        if (expression == null) {
          expression = new StringBuilder();
        } else {
          expression.setLength(0);
        }
        builder.append(src, offset, start - offset);
        offset = start + openToken.length();
        int end = text.indexOf(closeToken, offset);
        while (end > -1) {
          if ((end <= offset) || (src[end - 1] != '\\')) {
            expression.append(src, offset, end - offset);
            break;
          }
          // this close token is escaped. remove the backslash and continue.
          expression.append(src, offset, end - offset - 1).append(closeToken);
          offset = end + closeToken.length();
          end = text.indexOf(closeToken, offset);
        }
        if (end == -1) {
          // close token was not found.
          builder.append(src, start, src.length - start);
          offset = src.length;
        } else {
          /**
           * 首先根据参数的key，进行参数处理，返回？作为占位符
           */
          builder.append(handler.handleToken(expression.toString()));
          offset = end + closeToken.length();
        }
      }
      start = text.indexOf(openToken, offset);
    } while (start > -1);
    if (offset < src.length) {
      builder.append(src, offset, src.length - offset);
    }
    return builder.toString();
  }
}
