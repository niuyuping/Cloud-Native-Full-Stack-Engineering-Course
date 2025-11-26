#!/usr/bin/env python3
"""
递归导出 Jupyter Notebook 文件
在源目录下递归搜索所有 .ipynb 文件，并在每个文件所在目录导出同名的 .html 文件
"""
import subprocess
import sys
from pathlib import Path


def export_notebook(notebook_path):
    """导出单个 notebook 文件为 HTML"""
    notebook = Path(notebook_path).resolve()
    if not notebook.exists():
        print(f"错误：找不到文件 {notebook_path}")
        return False
    
    # 输出文件与源文件在同一目录
    output_dir = notebook.parent
    output_file = output_dir / f"{notebook.stem}.html"
    
    # 导出 HTML（隐藏代码）
    print(f"正在导出: {notebook}")
    result = subprocess.run([
        sys.executable, "-m", "nbconvert",
        "--to", "html",
        "--no-input",  # 隐藏代码
        str(notebook),
        "--output-dir", str(output_dir),
        "--output", notebook.stem
    ], capture_output=True, text=True, check=False)
    
    if result.returncode != 0:
        print(f"  ❌ 导出失败: {result.stderr}")
        return False
    else:
        print(f"  ✅ 已导出: {output_file.name}")
        return True


def find_and_export_notebooks(source_dir):
    """递归搜索并导出所有 .ipynb 文件"""
    source_path = Path(source_dir).resolve()
    
    if not source_path.exists():
        print(f"错误：源目录不存在 {source_dir}")
        return
    
    if not source_path.is_dir():
        print(f"错误：{source_dir} 不是一个目录")
        return
    
    # 递归搜索所有 .ipynb 文件
    notebook_files = list(source_path.rglob("*.ipynb"))
    
    if not notebook_files:
        print(f"在 {source_dir} 及其子目录中未找到 .ipynb 文件")
        return
    
    print(f"找到 {len(notebook_files)} 个 .ipynb 文件\n")
    
    # 统计信息
    success_count = 0
    fail_count = 0
    
    # 导出每个文件
    for notebook_file in sorted(notebook_files):
        if export_notebook(notebook_file):
            success_count += 1
        else:
            fail_count += 1
        print()  # 空行分隔
    
    # 输出统计信息
    print("=" * 50)
    print("导出完成！")
    print(f"成功: {success_count} 个")
    print(f"失败: {fail_count} 个")
    print(f"总计: {len(notebook_files)} 个")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("用法: python export_notebook.py <source_dir>")
        print("      递归搜索源目录下的所有 .ipynb 文件并导出为 HTML")
        sys.exit(1)
    
    source_directory = sys.argv[1]
    find_and_export_notebooks(source_directory)
