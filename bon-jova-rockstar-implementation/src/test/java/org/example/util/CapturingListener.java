package org.example.util;

import rock.Rockstar;
import rock.RockstarBaseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Used in tests as an alternative to mocking.
 */
public class CapturingListener extends RockstarBaseListener {
    private Rockstar.AssignmentStmtContext assignmentStatement;
    private Rockstar.PoeticNumberLiteralContext poeticNumberLiteral;
    private List<Rockstar.ExpressionContext> expressions = new ArrayList<>();
    private Rockstar.LiteralContext literal;
    private Rockstar.ConstantContext constant;

    private Rockstar.VariableContext variable;

    @Override
    public void enterAssignmentStmt(Rockstar.AssignmentStmtContext assignmentStatement) {
        this.assignmentStatement = assignmentStatement;
    }

    @Override
    public void enterPoeticNumberLiteral(Rockstar.PoeticNumberLiteralContext poeticNumberLiteral) {
        this.poeticNumberLiteral = poeticNumberLiteral;
    }

    @Override
    public void enterLiteral(Rockstar.LiteralContext literal) {
        this.literal = literal;
    }

    @Override
    public void enterConstant(Rockstar.ConstantContext constant) {
        this.constant = constant;
    }

    @Override
    public void enterVariable(Rockstar.VariableContext variable) {
        this.variable = variable;
    }

    @Override
    public void enterExpression(Rockstar.ExpressionContext expression) {
        this.expressions.add(expression);
    }


    public Rockstar.AssignmentStmtContext getAssignmentStatement() {
        return assignmentStatement;
    }

    public Rockstar.PoeticNumberLiteralContext getPoeticNumberLiteral() {
        return poeticNumberLiteral;
    }

    public Rockstar.ExpressionContext getExpression() {
        return expressions.get(expressions.size() - 1);
    }

    public Rockstar.ExpressionContext getExpression(int pos) {
        return expressions.get(0);
    }

    public Rockstar.LiteralContext getLiteral() {
        return literal;
    }

    public Rockstar.ConstantContext getConstant() {
        return constant;
    }

    public Rockstar.VariableContext getVariable() {
        return variable;
    }
}
