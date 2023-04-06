package delight.nashornsandbox;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import org.junit.Assert;
import org.junit.Test;

import delight.nashornsandbox.exceptions.ScriptCPUAbuseException;

public class TestEvalWithScriptContextWithNewBindings {
	@Test
	public void testWithNewBindings() throws ScriptCPUAbuseException, ScriptException {
		final NashornSandbox sandbox = NashornSandboxes.create();
		// Create new binding to override the ECMAScript Global properties
		Bindings newBinding = sandbox.createBindings();
		newBinding.put("Date", "2112018");

		final Object res = sandbox.eval("function method() { return parseInt(Date);} method();", newBinding);
    Assert.assertTrue(res.equals(Double.valueOf("2112018.0")) || res.equals(Integer.valueOf(2112018)));
	}

	@Test
	public void testWithNewSimpleBindings() throws ScriptCPUAbuseException, ScriptException {
		final NashornSandbox sandbox = NashornSandboxes.create();
		// Create new binding to override the ECMAScript Global properties
		Bindings newBinding = new SimpleBindings();
		newBinding.put("Date", "2112018");

		final Object res = sandbox.eval("function method() { return parseInt(Date);} method();", newBinding);
		Assert.assertTrue(Double.isNaN((Double) res));
	}

	@Test
	public void testWithNewBindingsScriptContext() throws ScriptCPUAbuseException, ScriptException {
		final NashornSandbox sandbox = NashornSandboxes.create();
		SandboxScriptContext newContext = sandbox.createScriptContext();
		// Create new binding to override the ECMAScript Global properties 
		Bindings newBinding = sandbox.createBindings();
		newBinding.put("Date", "2112018");
		newContext.getContext().setBindings(newBinding, ScriptContext.ENGINE_SCOPE);

		final Object res = sandbox.eval("function method() { return parseInt(Date);} method();", newContext);
    Assert.assertTrue(res.equals(Double.valueOf("2112018.0")) || res.equals(Integer.valueOf(2112018)));
	}

	@Test
	public void testWithExistingBindings() throws ScriptCPUAbuseException, ScriptException {
		final NashornSandbox sandbox = NashornSandboxes.create();
		SandboxScriptContext newContext = sandbox.createScriptContext();
		Bindings newBinding = newContext.getContext().getBindings(ScriptContext.ENGINE_SCOPE);
		newBinding.put("Date", "2112018");

		final Object res = sandbox.eval("function method() { return parseInt(Date);} method();", newContext);
		Assert.assertTrue(res.equals(2112018));
	}
	
	
}
