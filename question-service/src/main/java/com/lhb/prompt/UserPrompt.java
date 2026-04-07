package com.lhb.prompt;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lhb.dto.AiQuestionVectorDTO;
import com.lhb.entity.GradeInfo;
import com.lhb.entity.SysDict;
import com.lhb.enums.DictTypeEnum;
import com.lhb.enums.StageCodeEnum;
import com.lhb.service.GradeInfoService;
import com.lhb.service.KnowledgePointService;
import com.lhb.service.SubjectInfoService;
import com.lhb.service.SysDictService;
import com.lhb.vo.KnowledgePointVO;
import com.lhb.vo.SubjectInfoVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class UserPrompt {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private GradeInfoService gradeInfoService;

    @Resource
    private KnowledgePointService knowledgePointService;

    @Resource
    private SysDictService sysDictService;

    public String aiQuestionUserPrompt(AiQuestionVectorDTO dto) {
        StringBuilder sb = new StringBuilder();
        sb.append("请严格按照以下要求生成题目，仅返回标准JSON格式，不要任何额外说明、解释或markdown格式：\n");
        sb.append("### 题目生成参数\n");

        // 非空判断，避免空字符串
        if (StrUtil.isNotBlank(dto.getStageCode())) {
            StageCodeEnum anEnum = StageCodeEnum.getEnumByValue(dto.getStageCode());
            sb.append("- 学段：").append(anEnum.getMsg()).append("\n");
        }
        if (ObjUtil.isNotNull(dto.getSubjectId())) {
            SubjectInfoVO infoVO = subjectInfoService.getById(dto.getSubjectId());
            sb.append("- 科目：").append(infoVO.getSubjectName()).append("\n");
        }
        if (ObjUtil.isNotNull(dto.getGradeId())) {
            Map<Long, GradeInfo> gradeMap = gradeInfoService.getGradeMap(List.of(dto.getGradeId()));
            GradeInfo gradeInfo = gradeMap.get(dto.getGradeId());
            sb.append("- 年级：").append(gradeInfo.getGradeName()).append("\n");
        }
        if (ObjUtil.isNotNull(dto.getKpId())) {
            KnowledgePointVO kpVO = knowledgePointService.getById(dto.getKpId());
            sb.append("- 知识点：").append(kpVO.getKpName()).append("\n");
        }
        if (StrUtil.isNotBlank(dto.getQuestionTypeCode())) {
            Map<String,SysDict> dictMap = sysDictService.getDictMap(DictTypeEnum.QUESTION_TYPE);
            String dictName = dictMap.get(dto.getQuestionTypeCode().trim()).getDictName();
            sb.append("- 题型：").append(dictName).append("\n");
        }
        if (StrUtil.isNotBlank(dto.getDifficultyCode())) {
            Map<String,SysDict> dictMap = sysDictService.getDictMap(DictTypeEnum.DIFFICULTY);
            String dictName = dictMap.get(dto.getDifficultyCode().trim()).getDictName();
            sb.append("- 难度：").append(dictName).append("\n");
        }
        if (null == dto.getQuestionCount() || dto.getQuestionCount() <= 0 ) {
            dto.setQuestionCount(1L);
        }
        if(null != dto.getQuestionCount()) {
            sb.append("- 生成数量：").append(dto.getQuestionCount()).append("道\n");
        }

        if (StrUtil.isNotBlank(dto.getExtraRequirement())) {
            sb.append("- 额外要求：").append(dto.getExtraRequirement()).append("\n");
        }

        sb.append("\n### 输出要求\n");
        sb.append("1. 必须严格匹配上述学段、科目、年级、知识点、题型、难度要求\n");
        sb.append("2. 仅返回JSON数组，每个题目包含3个字段：\n");
        sb.append("   - content: 题干（完整题目内容）\n");
        sb.append("   - answer: 正确答案\n");
        sb.append("   - analysis: 详细题目解析\n");
        sb.append("3. 禁止返回任何非JSON内容，禁止使用```json等包裹\n");

        return sb.toString();
    }


}
